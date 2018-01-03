package com.jaspervanmerle.cglocal.view.center

import com.jaspervanmerle.cglocal.controller.ConnectedController
import com.jaspervanmerle.cglocal.view.center.connected.ConnectedMenuView
import tornadofx.*

class ConnectedView : View() {
    val controller: ConnectedController by inject()

    val menu: ConnectedMenuView by inject()

    override val root = borderpane {
        top = menu.root

        center {
            label("Connected")
        }
    }
}
