package com.jaspervanmerle.cglocal

import java.net.InetAddress
import java.net.ServerSocket
import javax.swing.JOptionPane

object CGLocal {
    var instanceSocket: ServerSocket? = null

    @JvmStatic
    fun main(args: Array<String>) {
        if (!isFirstInstance()) {
            val title = "CG Local"
            val message = "Only one instance of CG Local can be running at a time.\nPlease use the running instance."
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE)
            System.exit(0)
        }
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
