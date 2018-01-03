package com.jaspervanmerle.cglocal.view

import com.jaspervanmerle.cglocal.controller.MainController
import com.jaspervanmerle.cglocal.view.center.DisconnectedView
import tornadofx.*

class MainView : View("CG Local") {
    val controller: MainController by inject()

    val menuView: MenuView by inject()
    val disconnectedView: DisconnectedView by inject()
    val statusView: StatusView by inject()

    override val root = borderpane {
        top = menuView.root
        center = disconnectedView.root
        bottom = statusView.root
    }

    init {
        currentStage?.width = 400.0
        currentStage?.height = 500.0
        currentStage?.isResizable = false

        controller.init()
    }
}
