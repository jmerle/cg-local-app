package com.jaspervanmerle.cglocal.controller

import com.jaspervanmerle.cglocal.CGLocal
import com.jaspervanmerle.cglocal.Config
import com.jaspervanmerle.cglocal.Constants
import com.jaspervanmerle.cglocal.server.Server
import com.jaspervanmerle.cglocal.util.ObservableProperty
import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.util.setCenter
import com.jaspervanmerle.cglocal.util.setStatus
import com.jaspervanmerle.cglocal.view.center.ConnectedView
import com.jaspervanmerle.cglocal.view.center.DisconnectedView
import com.jaspervanmerle.cglocal.view.center.connected.FirstActionFragment
import com.jaspervanmerle.cglocal.view.center.connected.SelectFileFragment
import com.jaspervanmerle.cglocal.view.center.connected.SetupCompletedFragment
import mu.KLogging
import name.mitterdorfer.perlock.EventKind
import name.mitterdorfer.perlock.PathWatcher
import name.mitterdorfer.perlock.PathWatcherFactory
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.SwingUtilities
import kotlin.concurrent.schedule

class ConnectedController {
    companion object : KLogging()

    private val frame: CGLocal by koin.inject()
    private val server: Server by koin.inject()
    private val config: Config by koin.inject()
    private val connectedView: ConnectedView by koin.inject()

    private val titleProperty = ObservableProperty("")
    private var title by titleProperty

    private val firstActionDisabledProperty = ObservableProperty(false)
    private var firstActionDisabled by firstActionDisabledProperty

    private var questionId = -1
    private var selectedFile = File("")

    private var watcher: PathWatcher? = null
    private var watcherExecutor: ExecutorService? = null

    private var ignoreEditorChange = false
    private var ignoreFileChange = 0

    fun init(title: String, questionId: Int) {
        this.title = title
        this.questionId = questionId

        firstActionDisabled = false

        setCenter(ConnectedView::class)
        setCenter(SelectFileFragment::class, ConnectedView::class)
        setStatus("Connected")

        frame.extendedState = JFrame.NORMAL
        frame.isVisible = true
        frame.toFront()
    }

    fun disconnect() {
        watcher?.stop()
        watcherExecutor?.shutdown()

        setCenter(DisconnectedView::class)
        setStatus("Listening on port ${Constants.WEB_SOCKET_PORT}")
    }

    private fun usePreviouslySelectedFile() {
        val path = config.getPreviouslySelectedFile(questionId)
        continueWithFile(File(path))
    }

    private fun selectFile() {
        val fileChooser = JFileChooser().apply {
            dialogTitle = "Select a file"
        }

        val result = fileChooser.showOpenDialog(connectedView)
        if (result == JFileChooser.APPROVE_OPTION) {
            continueWithFile(fileChooser.selectedFile)
        }
    }

    private fun continueWithFile(file: File) {
        config.setSelectedFile(questionId, file.absolutePath)
        selectedFile = file

        firstActionDisabled = false

        when (config.defaultAction) {
            "ask" -> setCenter(FirstActionFragment::class, ConnectedView::class)
            "download" -> firstActionDownload()
            "upload" -> firstActionUpload()
        }
    }

    private fun firstActionDownload() {
        firstActionDisabled = true

        server.getCode {
            selectedFile.writeText(it)
            setupCompleted()
        }
    }

    private fun firstActionUpload() {
        firstActionDisabled = true
        server.updateCode(getSelectedFileCode(), false)
        setupCompleted()
    }

    private fun firstActionChangeFile() {
        firstActionDisabled = true
        setCenter(SelectFileFragment::class, ConnectedView::class)
    }

    private fun setupCompleted() {
        watcherExecutor = Executors.newFixedThreadPool(1)
        val factory = PathWatcherFactory(watcherExecutor)

        watcher = factory.createNonRecursiveWatcher(Paths.get(selectedFile.parent)) { eventKind: EventKind, path: Path ->
            logger.info("Path: $path")
            logger.info("Event kind: $eventKind")

            if (path == selectedFile.toPath()) {
                when (eventKind) {
                    EventKind.CREATE -> {
                        SwingUtilities.invokeLater {
                            onSelectedFileChange()
                        }
                    }
                    EventKind.MODIFY -> {
                        SwingUtilities.invokeLater {
                            onSelectedFileChange()
                        }
                    }
                    EventKind.DELETE -> {
                        val timer = Timer()
                        timer.schedule(250) {
                            SwingUtilities.invokeLater {
                                onSelectedFileDeleted()
                                timer.cancel()
                                timer.purge()
                            }
                        }
                    }
                    else -> {
                    }
                }
            }
        }

        watcher?.start()

        if (config.twoWayDataBinding) {
            server.setReadOnly(false)
        }

        setCenter(SetupCompletedFragment::class, ConnectedView::class)
    }

    private fun forceDownload() {
        server.getCode {
            ignoreFileChange = 2
            selectedFile.writeText(it)
        }
    }

    private fun forceUpload() {
        server.updateCode(getSelectedFileCode(), config.autoPlay)
    }

    fun onEditorChange(code: String) {
        if (config.twoWayDataBinding && firstActionDisabled && !ignoreEditorChange) {
            logger.info("Synchronizing editor with local file")

            ignoreFileChange = 2
            selectedFile.writeText(code)
        }

        ignoreEditorChange = false
    }

    private fun onSelectedFileChange() {
        if (ignoreFileChange == 0) {
            logger.info("Synchronizing local file with editor")

            ignoreEditorChange = true
            server.updateCode(getSelectedFileCode(), config.autoPlay)
        }

        if (ignoreFileChange > 0) {
            ignoreFileChange--
        }
    }

    private fun onSelectedFileDeleted() {
        if (!selectedFile.exists()) {
            val message = "The local file was deleted"
            logger.error(message)
            server.error(message)
        }
    }

    private fun getSelectedFileCode(): String {
        val sb = StringBuilder()

        selectedFile.forEachLine {
            sb.append(it)
            sb.append("\n")
        }

        return sb.toString()
    }
}
