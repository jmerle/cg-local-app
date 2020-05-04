package com.jaspervanmerle.cglocal.server

import com.jaspervanmerle.cglocal.Constants
import com.jaspervanmerle.cglocal.controller.ConnectedController
import com.jaspervanmerle.cglocal.util.errorAndExit
import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.util.setStatus
import mu.KLogging
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import org.json.JSONObject
import java.net.BindException
import java.net.InetSocketAddress
import javax.swing.SwingUtilities

class Server : WebSocketServer(InetSocketAddress(Constants.WEB_SOCKET_PORT)) {
    companion object : KLogging()

    init {
        isReuseAddr = true
    }

    private var connectedSocket: WebSocket? = null
    private val connectedController: ConnectedController by koin.inject()

    val connected: Boolean
        get() = connectedSocket != null

    private val codeCallbacks = ArrayList<(code: String) -> Unit>()

    override fun onStart() {
        logger.info("Started listening on port $port")

        SwingUtilities.invokeLater {
            setStatus("Listening on port ${Constants.WEB_SOCKET_PORT}")
        }
    }

    override fun onError(conn: WebSocket?, ex: Exception) {
        logger.error(ex) { "${conn?.remoteSocketAddress ?: "Something"} threw an exception" }

        if (ex is BindException) {
            errorAndExit("Server could not bind to port ${Constants.WEB_SOCKET_PORT}.\nMake sure it is not being used by another application.")
        }
    }

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

            SwingUtilities.invokeLater {
                connectedController.disconnect()
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

                SwingUtilities.invokeLater {
                    connectedController.init(title, questionId)
                    conn.send(ServerMessageAction.APP_READY)
                }
            }
            ServerMessageAction.CODE -> {
                val code = msg.payload.getString("code")

                SwingUtilities.invokeLater {
                    if (codeCallbacks.size == 0) {
                        connectedController.onEditorChange(code)
                    } else {
                        for (cb in codeCallbacks) {
                            cb(code)
                        }

                        codeCallbacks.clear()
                    }
                }
            }
            else -> {
            }
        }
    }

    fun updateCode(code: String, play: Boolean) {
        send(ServerMessageAction.UPDATE_CODE, JSONObject(mapOf(
            "code" to code,
            "play" to play
        )))
    }

    fun getCode(callback: (code: String) -> Unit) {
        if (send(ServerMessageAction.SEND_CODE)) {
            codeCallbacks.add(callback)
        }
    }

    fun setReadOnly(state: Boolean) {
        send(ServerMessageAction.SET_READ_ONLY, JSONObject(mapOf("state" to state)))
    }

    fun error(message: String) {
        send(ServerMessageAction.ERROR, JSONObject(mapOf("message" to message)))
    }

    fun closeConnectedSocket() {
        connectedSocket?.close()
    }

    private fun WebSocket.send(action: ServerMessageAction, payload: JSONObject = JSONObject()) {
        val message = ServerMessage(action, payload).toString()

        logger.info("Sending message to ${remoteSocketAddress}:")
        logger.info(message)

        send(message)
    }

    private fun send(action: ServerMessageAction, payload: JSONObject = JSONObject()): Boolean {
        if (connectedSocket == null) {
            return false
        }

        connectedSocket!!.send(action, payload)
        return true
    }
}
