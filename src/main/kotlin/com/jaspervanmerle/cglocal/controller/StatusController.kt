package com.jaspervanmerle.cglocal.controller

import com.jaspervanmerle.cglocal.util.ObservableProperty

class StatusController {
    val statusProperty = ObservableProperty("Loading")
    var status by statusProperty
}
