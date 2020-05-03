package com.jaspervanmerle.cglocal

import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.util.frame
import com.jaspervanmerle.cglocal.view.MainView
import java.net.InetAddress
import java.net.ServerSocket
import javax.swing.JFrame
import javax.swing.JOptionPane
import kotlin.system.exitProcess

class CGLocal {
    private lateinit var instanceSocket: ServerSocket

    private val mainView: MainView by koin.inject()

    fun start() {
        ensureSingleInstance()

        frame("CG Local") {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            isResizable = false

            contentPane = mainView

            pack()
            isVisible = true
            setLocationRelativeTo(null)
        }
    }

    private fun ensureSingleInstance() {
        if (isFirstInstance()) {
            return
        }

        val title = "CG Local"
        val message = "Only one instance of CG Local can be running at a time.\nPlease use the running instance."
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE)

        exitProcess(1)
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
