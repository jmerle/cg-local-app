package com.jaspervanmerle.cglocal.controller

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class StatusController : Controller() {
    val statusProperty = SimpleStringProperty("Loading")
    var status by statusProperty
}
