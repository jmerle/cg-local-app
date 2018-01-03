package com.jaspervanmerle.cglocal.server

import com.jaspervanmerle.cglocal.Constants
import com.jaspervanmerle.cglocal.controller.ConnectedController
import mu.KotlinLogging
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import tornadofx.*
import java.lang.Exception
import java.net.InetSocketAddress

object Server : WebSocketServer(InetSocketAddress(Constants.WEB_SOCKET_PORT)) {
    val logger = KotlinLogging.logger {}

    var connectedSocket: WebSocket? = null
    val controller = find(ConnectedController::class)

    val connected: Boolean
        get() = connectedSocket != null

    val codeCallbacks = ArrayList<CodeCallback>()

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        logger.info("${conn.remoteSocketAddress} opened a connected")

        if (connectedSocket == null) {
            logger.info("Accepting ${conn.remoteSocketAddress}")

            connectedSocket = conn
            conn.send(ServerMessageAction.SEND_DETAILS)
        } else {
            logger.info("Denying ${conn.remoteSocketAddress}, application already in use")

            conn.send(ServerMessageAction.ALREADY_CONNECTED)
        }
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        logger.info("${conn.remoteSocketAddress} closed a connection")

        if (conn === connectedSocket) {
            logger.info("Application became available again")

            connectedSocket = null
            codeCallbacks.clear()

            runLater {
                controller.disconnect()
            }
        }
    }

    override fun onMessage(conn: WebSocket, message: String) {
        logger.info("${conn.remoteSocketAddress} sent a message:")
        logger.info(message)

        val msg = ServerMessage.fromMessage(message)

        when (msg.action) {
            ServerMessageAction.DETAILS -> {
                val title = msg.payload.getString("title")
                val questionId = msg.payload.getInt("questionId")

                runLater {
                    controller.init(title, questionId)
                    conn.send(ServerMessageAction.APP_READY)
                }
            }
            ServerMessageAction.CODE -> {
                val code = msg.payload.getString("code")

                runLater {
                    if (codeCallbacks.size == 0) {
                        controller.onEditorChange(code)
                    } else {
                        for (cb in codeCallbacks) {
                            cb(code)
                        }

                        codeCallbacks.clear()
                    }
                }
            }
            else -> {}
        }
    }

    override fun onError(conn: WebSocket?, ex: Exception) {
        logger.error(ex) { "${conn?.remoteSocketAddress ?: "Something"} threw an exception" }
    }

    override fun onStart() {
        logger.info("Started listening on port $port")
    }
}
