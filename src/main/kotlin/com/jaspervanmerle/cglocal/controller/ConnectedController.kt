package com.jaspervanmerle.cglocal.controller

import com.jaspervanmerle.cglocal.Config
import com.jaspervanmerle.cglocal.Constants
import com.jaspervanmerle.cglocal.server.*
import com.jaspervanmerle.cglocal.util.getSource
import com.jaspervanmerle.cglocal.util.setCenter
import com.jaspervanmerle.cglocal.util.setStatus
import com.jaspervanmerle.cglocal.view.center.ConnectedView
import com.jaspervanmerle.cglocal.view.center.DisconnectedView
import com.jaspervanmerle.cglocal.view.center.connected.FirstActionFragment
import com.jaspervanmerle.cglocal.view.center.connected.SelectFileFragment
import com.jaspervanmerle.cglocal.view.center.connected.SetupCompletedFragment
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.stage.FileChooser
import mu.KLogging
import name.mitterdorfer.perlock.EventKind
import name.mitterdorfer.perlock.PathWatcher
import name.mitterdorfer.perlock.PathWatcherFactory
import tornadofx.*
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ConnectedController : Controller() {
    companion object : KLogging()

    val titleProperty = SimpleStringProperty("")
    var title by titleProperty

    val firstActionDisabledProperty = SimpleBooleanProperty(false)
    var firstActionDisabled by firstActionDisabledProperty

    var questionId = -1
    var selectedFile = File("")

    var watcher: PathWatcher? = null
    var watcherExecutor: ExecutorService? = null

    var ignoreEditorChange = false
    var ignoreFileChange = 0

    fun init(title: String, questionId: Int) {
        this.title = title
        this.questionId = questionId

        firstActionDisabled = false

        setCenter(ConnectedView::class)
        setCenter(SelectFileFragment::class, ConnectedView::class)
        setStatus("Connected")

        primaryStage.isIconified = false
        primaryStage.show()
        primaryStage.toFront()
    }

    fun disconnect() {
        watcher?.stop()
        watcherExecutor?.shutdown()

        setCenter(DisconnectedView::class)
        setStatus("Listening on port ${Constants.WEB_SOCKET_PORT}")
    }

    fun usePreviouslySelectedFile() {
        val path = Config.getPreviouslySelectedFile(questionId)
        continueWithFile(File(path))
    }

    fun selectFile() {
        val fileChooser = FileChooser()
        fileChooser.title = "Select a file"

        val window = find(ConnectedView::class).currentWindow
        val file = fileChooser.showOpenDialog(window)

        if (file != null) {
            continueWithFile(file)
        }
    }

    fun continueWithFile(file: File) {
        Config.setSelectedFile(questionId, file.absolutePath)
        selectedFile = file

        firstActionDisabled = false

        when (Config.defaultAction) {
            "ask" -> setCenter(FirstActionFragment::class, ConnectedView::class)
            "download" -> firstActionDownload()
            "upload" -> firstActionUpload()
        }
    }

    fun firstActionDownload() {
        firstActionDisabled = true

        Server.connectedSocket?.getCode {
            selectedFile.writeText(it)
            setupCompleted()
        }
    }

    fun firstActionUpload() {
        firstActionDisabled = true
        Server.connectedSocket?.updateCode(selectedFile.getSource(), false)
        setupCompleted()
    }

    fun firstActionChangeFile() {
        firstActionDisabled = true
        setCenter(SelectFileFragment::class, ConnectedView::class)
    }

    fun setupCompleted() {
        watcherExecutor = Executors.newFixedThreadPool(1)
        val factory = PathWatcherFactory(watcherExecutor)

        watcher = factory.createNonRecursiveWatcher(Paths.get(selectedFile.parent)) {
            eventKind: EventKind, path: Path ->
            if (path == selectedFile.toPath()) {
                when (eventKind) {
                    EventKind.MODIFY -> {
                        runLater {
                            onSelectedFileChange()
                        }
                    }
                    EventKind.DELETE -> {
                        runLater {
                            onSelectedFileDeleted()
                        }
                    }
                    else -> {}
                }
            }
        }

        watcher?.start()

        if (Config.twoWayDataBinding) {
            Server.connectedSocket?.setReadOnly(false)
        }

        setCenter(SetupCompletedFragment::class, ConnectedView::class)
    }

    fun forceDownload() {
        Server.connectedSocket?.getCode {
            ignoreFileChange = 2
            selectedFile.writeText(it)
        }
    }

    fun forceUpload() {
        Server.connectedSocket?.updateCode(selectedFile.getSource(), Config.autoPlay)
    }

    fun onEditorChange(code: String) {
        if (Config.twoWayDataBinding && firstActionDisabled && !ignoreEditorChange) {
            logger.info("Synchronizing editor with local file")

            ignoreFileChange = 2
            selectedFile.writeText(code)
        }

        ignoreEditorChange = false
    }

    fun onSelectedFileChange() {
        if (selectedFile.exists()) {
            if (ignoreFileChange == 0) {
                logger.info("Synchronizing local file with editor")

                ignoreEditorChange = true
                Server.connectedSocket?.updateCode(selectedFile.getSource(), Config.autoPlay)
            }

            if (ignoreFileChange > 0) ignoreFileChange--
        } else {
            onSelectedFileDeleted()
        }
    }

    fun onSelectedFileDeleted() {
        logger.error("The local file was deleted")
        Server.connectedSocket?.error("The local file has been deleted!")
    }
}
