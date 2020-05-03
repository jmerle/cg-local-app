package com.jaspervanmerle.cglocal.view

import com.jaspervanmerle.cglocal.Constants
import com.jaspervanmerle.cglocal.controller.MainController
import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.view.center.DisconnectedView
import java.awt.BorderLayout
import java.awt.Dimension

class MainView : View(BorderLayout()) {
    private val mainController: MainController by koin.inject()

    private val menuView: MenuView by koin.inject()
    private val disconnectedView: DisconnectedView by koin.inject()
    private val statusView: StatusView by koin.inject()

    init {
        preferredSize = Dimension(Constants.WIDTH, Constants.HEIGHT)

        add(menuView, BorderLayout.NORTH)
        add(disconnectedView, BorderLayout.CENTER)
        add(statusView, BorderLayout.SOUTH)

        mainController.start()
    }
}
