package com.jaspervanmerle.cglocal.view

import com.jaspervanmerle.cglocal.controller.StatusController
import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.util.label
import javax.swing.JPanel

class StatusView : JPanel() {
    private val controller: StatusController by koin.inject()

    init {
        label(controller.statusProperty)
    }
}
