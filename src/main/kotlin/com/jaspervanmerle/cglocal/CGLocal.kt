package com.jaspervanmerle.cglocal

import com.jaspervanmerle.cglocal.controller.MainController
import com.jaspervanmerle.cglocal.util.errorAndExit
import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.view.MainView
import mu.KLogging
import java.awt.Toolkit
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.net.InetAddress
import java.net.ServerSocket
import javax.swing.JFrame
import javax.swing.WindowConstants
import kotlin.concurrent.thread

class CGLocal : JFrame() {
    companion object : KLogging() {
        var stopping = false
    }

    private val mainView: MainView by koin.inject()
    private val mainController: MainController by koin.inject()

    private lateinit var instanceSocket: ServerSocket

    fun start() {
        ensureSingleInstance()
        addShutdownHook()
        addWindowCloseListener()
        configureFrame()
        showFrame()
    }

    private fun ensureSingleInstance() {
        if (isFirstInstance()) {
            return
        }

        errorAndExit("Only one instance of CG Local can be running at a time.<br>Please use the running instance.")
    }

    private fun isFirstInstance(): Boolean {
        return try {
            val address = InetAddress.getByAddress(arrayOf(127.toByte(), 0, 0, 1).toByteArray())
            instanceSocket = ServerSocket(Constants.LOCK_PORT, 0, address)
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(thread(false) {
            stop()
        })
    }

    private fun addWindowCloseListener() {
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                stop()
            }
        })
    }

    private fun stop() {
        if (stopping) {
            return
        }

        stopping = true
        mainController.stop()
    }

    private fun configureFrame() {
        title = "CG Local"
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false

        iconImages = arrayOf(16, 19, 20, 24, 32, 38, 48, 64, 96)
            .map { this::class.java.classLoader.getResource("icons/icon-$it.png") }
            .map { Toolkit.getDefaultToolkit().getImage(it) }

        try {
            val toolkit = Toolkit.getDefaultToolkit()
            val toolkitClass = toolkit::class.java
            if (toolkitClass.name == "sun.awt.X11.XToolkit") {
                logger.info("Setting application title, ignore the reflective access warning")
                val awtAppClassName = toolkitClass.getDeclaredField("awtAppClassName")
                awtAppClassName.isAccessible = true
                awtAppClassName.set(toolkit, "CG Local")
            }
        } catch (err: Exception) {
            // Do nothing
        }

        contentPane = mainView
    }

    private fun showFrame() {
        pack()
        isVisible = true
        setLocationRelativeTo(null)
    }
}
