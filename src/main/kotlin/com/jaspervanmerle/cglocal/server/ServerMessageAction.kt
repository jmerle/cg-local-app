package com.jaspervanmerle.cglocal.server

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
