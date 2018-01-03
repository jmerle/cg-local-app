package com.jaspervanmerle.cglocal.server

import mu.KotlinLogging
import org.java_websocket.WebSocket
import org.json.JSONObject

val logger = KotlinLogging.logger {}

data class ServerMessage(val action: ServerMessageAction, val payload: JSONObject = JSONObject()) {
    companion object {
        fun fromMessage(message: String): ServerMessage {
            val obj = JSONObject(message)

            val actionStr = obj.getString("action")
            val action = ServerMessageAction.values().first { it.value == actionStr }
            val payload = obj.getJSONObject("payload")

            return ServerMessage(action, payload)
        }
    }

    override fun toString(): String {
        val obj = JSONObject()

        obj.put("action", action.value)
        obj.put("payload", payload)

        return obj.toString()
    }
}

enum class ServerMessageAction(val value: String) {
    SEND_DETAILS("send-details"),
    DETAILS("details"),
    APP_READY("app-ready"),
    ALREADY_CONNECTED("already-connected"),
    UPDATE_CODE("update-code"),
    SEND_CODE("send-code"),
    CODE("code"),
    SET_READ_ONLY("set-read-only"),
    ERROR("error")
}

fun WebSocket.send(action: ServerMessageAction, payload: JSONObject = JSONObject()) {
    val message = ServerMessage(action, payload).toString()

    logger.info("Sending message to $remoteSocketAddress:")
    logger.info(message)

    send(message)
}

fun WebSocket.updateCode(code: String, play: Boolean) {
    send(ServerMessageAction.UPDATE_CODE, JSONObject(mapOf(
        "code" to code,
        "play" to play
    )))
}

typealias CodeCallback = (String) -> Unit

fun WebSocket.getCode(cb: CodeCallback) {
    send(ServerMessageAction.SEND_CODE)
    Server.codeCallbacks.add(cb)
}

fun WebSocket.setReadOnly(state: Boolean) {
    send(ServerMessageAction.SET_READ_ONLY, JSONObject(mapOf("state" to state)))
}

fun WebSocket.error(message: String) {
    send(ServerMessageAction.ERROR, JSONObject(mapOf("message" to message)))
}
