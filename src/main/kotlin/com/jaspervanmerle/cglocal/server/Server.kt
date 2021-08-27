package com.jaspervanmerle.cglocal.server

import com.jaspervanmerle.cglocal.Constants
import com.jaspervanmerle.cglocal.controller.ConnectedController
import com.jaspervanmerle.cglocal.util.errorAndExit
import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.util.setStatus
import mu.KLogging
import org.java_websocket.WebSocket
import org.json.JSONObject
import java.net.BindException
import java.nio.ByteBuffer
import javax.swing.SwingUtilities

class Server : HttpWsServer(Constants.WEB_SOCKET_PORT) {
    companion object : KLogging()

    private var connectedSocket: WebSocket? = null
    private val connectedController: ConnectedController by koin.inject()

    val connected: Boolean
        get() = connectedSocket != null

    private val codeCallbacks = ArrayList<(code: String) -> Unit>()

    override fun onStart() {
        logger.info("Started listening on port ${Constants.WEB_SOCKET_PORT}")

        SwingUtilities.invokeLater {
            setStatus("Listening on port ${Constants.WEB_SOCKET_PORT}")
        }
    }

    override fun onError(connection: WebSocket?, e: Exception) {
        logger.error(e) { "${connection?.remoteSocketAddress ?: "Something"} threw an exception" }

        if (e is BindException) {
            errorAndExit("Server could not bind to port ${Constants.WEB_SOCKET_PORT}.<br>Make sure it is not being used by another application.")
        }
    }

    override fun onWsOpen(connection: WebSocket) {
        logger.info("${connection.remoteSocketAddress} opened a connected")

        if (connectedSocket == null) {
            logger.info("Accepting ${connection.remoteSocketAddress}")

            connectedSocket = connection
            connection.send(ServerMessageAction.SEND_DETAILS)
        } else {
            logger.info("Denying ${connection.remoteSocketAddress}, application already in use")

            connection.send(ServerMessageAction.ALREADY_CONNECTED)
        }
    }

    override fun onWsClose(connection: WebSocket) {
        logger.info("${connection.remoteSocketAddress} closed a connection")

        if (connection === connectedSocket) {
            logger.info("Application became available again")

            connectedSocket = null
            codeCallbacks.clear()

            SwingUtilities.invokeLater {
                connectedController.disconnect()
            }
        }
    }

    override fun onWsMessage(connection: WebSocket, message: String) {
        logger.info("${connection.remoteSocketAddress} sent a message:")
        logger.info(message)

        val msg = ServerMessage.fromMessage(message)

        when (msg.action) {
            ServerMessageAction.DETAILS -> {
                val title = msg.payload.getString("title")
                val questionId = msg.payload.getInt("questionId")

                SwingUtilities.invokeLater {
                    connectedController.init(title, questionId)
                    connection.send(ServerMessageAction.APP_READY)
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

    override fun onWsMessage(connection: WebSocket, message: ByteBuffer) {
        onWsMessage(connection, message.toString())
    }

    override fun onGetRequest(path: String): GetRequestResult {
        logger.info("GET $path")

        when (path.trimEnd('/')) {
            "/play" -> {
                connectedController.forceUpload(true)
            }
        }

        return GetRequestResult(200, "OK", "text/html", "OK".toByteArray())
    }

    fun updateCode(code: String, play: Boolean) {
        send(
            ServerMessageAction.UPDATE_CODE, JSONObject(
                mapOf(
                    "code" to code,
                    "play" to play
                )
            )
        )
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
