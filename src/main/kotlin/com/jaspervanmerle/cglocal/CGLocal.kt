package com.jaspervanmerle.cglocal

import com.jaspervanmerle.cglocal.controller.MainController
import com.jaspervanmerle.cglocal.view.MainView
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.stage.Stage
import tornadofx.*
import java.net.InetAddress
import java.net.ServerSocket

class CGLocal : App(MainView::class, Styles::class) {
    companion object {
        var instanceSocket: ServerSocket? = null
        var stopping = false

        var version = ""
    }

    init {
        if (!isFirstInstance()) {
            val alert = Alert(AlertType.INFORMATION)

            alert.title = "CGLocal"
            alert.headerText = null
            alert.contentText = "Only one instance of CG Local can be running at a time. Please use the running instance."

            alert.showAndWait()
            System.exit(0)
        }

        // Improve font rendering on Linux
        System.setProperty("prism.lcdtext", "false")
    }

    override fun start(stage: Stage) {
        if (parameters != null) {
            version = parameters.named["version"] ?: "Development"
        }

        super.start(stage)
    }

    override fun stop() {
        stopping = true
        find(MainController::class).stop()
        super.stop()
    }

    fun isFirstInstance(): Boolean {
        return try {
            val address = InetAddress.getByAddress(arrayOf(127.toByte(), 0, 0, 1).toByteArray())
            instanceSocket = ServerSocket(Constants.LOCK_PORT, 0, address)
            true
        } catch (e: Exception) {
            false
        }
    }
}
