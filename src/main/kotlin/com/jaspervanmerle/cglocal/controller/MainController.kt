package com.jaspervanmerle.cglocal.controller

import com.jaspervanmerle.cglocal.Constants
import com.jaspervanmerle.cglocal.server.Server
import com.jaspervanmerle.cglocal.util.setStatus
import javafx.scene.image.Image
import tornadofx.*

class MainController : Controller() {
    fun init() {
        arrayOf(16, 19, 20, 24, 32, 38, 48, 64, 96)
            .map { this::class.java.classLoader.getResourceAsStream("icons/icon-$it.png") }
            .forEach { primaryStage.icons.add(Image(it)) }

        setStatus("Starting server")
        Server.start()
        setStatus("Listening on port ${Constants.WEB_SOCKET_PORT}")
    }

    fun stop() {
        setStatus("Stopping server")
        Server.stop()
        setStatus("Shutting down")
    }
}
