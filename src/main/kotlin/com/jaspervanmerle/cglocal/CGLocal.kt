package com.jaspervanmerle.cglocal

import com.jaspervanmerle.cglocal.controller.MainController
import com.jaspervanmerle.cglocal.util.errorAndExit
import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.view.MainView
import jiconfont.icons.font_awesome.FontAwesome
import jiconfont.swing.IconFontSwing
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.net.InetAddress
import java.net.ServerSocket
import javax.swing.JFrame
import javax.swing.WindowConstants
import kotlin.concurrent.thread

class CGLocal : JFrame() {
    companion object {
        var stopping = false
    }

    private lateinit var instanceSocket: ServerSocket

    private val mainView: MainView by koin.inject()
    private val mainController: MainController by koin.inject()

    fun start() {
        if (!isFirstInstance()) {
            errorAndExit("Only one instance of CG Local can be running at a time.\nPlease use the running instance.")
        }

        Runtime.getRuntime().addShutdownHook(thread(false) {
            stop()
        })

        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                stop()
            }
        })

        IconFontSwing.register(FontAwesome.getIconFont())

        title = "CG Local"
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false

        contentPane = mainView

        pack()
        isVisible = true
        setLocationRelativeTo(null)
    }

    private fun stop() {
        if (stopping) {
            return
        }

        stopping = true
        mainController.stop()
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
}
