package com.jaspervanmerle.cglocal.server

import org.json.JSONObject

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
