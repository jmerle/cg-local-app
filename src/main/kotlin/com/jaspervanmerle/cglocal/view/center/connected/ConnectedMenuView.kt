package com.jaspervanmerle.cglocal.view.center.connected

import com.jaspervanmerle.cglocal.Constants
import com.jaspervanmerle.cglocal.controller.ConnectedController
import com.jaspervanmerle.cglocal.server.Server
import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.view.View
import jiconfont.icons.font_awesome.FontAwesome
import jiconfont.swing.IconFontSwing
import java.awt.Dimension
import java.awt.Font

class ConnectedMenuView : View("insets 0 10 0 10, aligny center", "[grow]") {
    private val connectedController: ConnectedController by koin.inject()
    private val server: Server by koin.inject()

    init {
        preferredSize = Dimension(Constants.WIDTH, 50)
        background = Constants.ORANGE

        label(connectedController.titleProperty) {
            font = font.deriveFont(Font.BOLD, 20f)
        }

        button(IconFontSwing.buildIcon(FontAwesome.TIMES, 20f, Constants.BLACK), "right") {
            addActionListener {
                server.closeConnectedSocket()
            }
        }
    }
}
