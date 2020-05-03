package com.jaspervanmerle.cglocal.view

import com.jaspervanmerle.cglocal.controller.MainController
import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.view.center.DisconnectedView
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JPanel

class MainView : JPanel() {
    private val mainController: MainController by koin.inject()

    private val menuView: MenuView by koin.inject()
    private val disconnectedView: DisconnectedView by koin.inject()
    private val statusView: StatusView by koin.inject()

    init {
        layout = BorderLayout()
        preferredSize = Dimension(400, 500)

        add(menuView, BorderLayout.NORTH)
        add(disconnectedView, BorderLayout.CENTER)
        add(statusView, BorderLayout.SOUTH)

        mainController.start()
    }
}
