package com.jaspervanmerle.cglocal.view

import com.jaspervanmerle.cglocal.Constants
import com.jaspervanmerle.cglocal.controller.SettingsController
import com.jaspervanmerle.cglocal.util.koin
import java.awt.Dimension
import java.awt.Font

class MenuView : View("insets 0 10 0 10, aligny center", "[grow]") {
    private val settingsController: SettingsController by koin.inject()

    init {
        preferredSize = Dimension(Constants.WIDTH, 50)
        background = Constants.BLACK

        label("CG".color(Constants.WHITE) + "Local".color(Constants.ORANGE)) {
            font = font.deriveFont(Font.BOLD, 26f)
        }

        button(settingsController.iconProperty, "right") {
            addActionListener {
                settingsController.toggleSettings()
            }
        }
    }
}
