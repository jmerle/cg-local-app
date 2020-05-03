package com.jaspervanmerle.cglocal.controller

import com.jaspervanmerle.cglocal.server.Server
import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.util.setStatus
import mu.KLogging

class MainController : KLogging() {
    private val server: Server by koin.inject()

    fun start() {
        setStatus("Starting server")
        server.start()
    }

    fun stop() {
        logger.info("Shutting down")
        server.stop()
    }
}
