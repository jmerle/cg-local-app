package com.jaspervanmerle.cglocal.view.center

import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.view.View
import com.jaspervanmerle.cglocal.view.center.connected.ConnectedMenuView
import java.awt.BorderLayout

class ConnectedView : View(BorderLayout()) {
    private val connectedMenuView: ConnectedMenuView by koin.inject()

    init {
        add(connectedMenuView, BorderLayout.NORTH)

        label("Connected", BorderLayout.CENTER)
    }
}
