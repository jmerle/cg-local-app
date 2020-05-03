package com.jaspervanmerle.cglocal.view

import com.jaspervanmerle.cglocal.Constants
import com.jaspervanmerle.cglocal.controller.SettingsController
import com.jaspervanmerle.cglocal.util.koin
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Font
import javax.swing.border.EmptyBorder

class MenuView : View() {
    private val settingsController: SettingsController by koin.inject()

    init {
        layout = BorderLayout()
        border = EmptyBorder(0, 10, 0, 10)

        preferredSize = Dimension(Constants.WIDTH, 50)
        background = Constants.BLACK

        label("CG".color(Constants.WHITE) + "Local".color(Constants.ORANGE), BorderLayout.WEST) {
            font = Font(font.name, Font.BOLD, 26)
        }

        button(settingsController.iconProperty, BorderLayout.EAST) {
            addActionListener {
                settingsController.toggleSettings()
            }
        }
    }
}
