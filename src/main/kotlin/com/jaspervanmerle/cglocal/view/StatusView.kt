package com.jaspervanmerle.cglocal.view

import com.jaspervanmerle.cglocal.Constants
import com.jaspervanmerle.cglocal.controller.StatusController
import com.jaspervanmerle.cglocal.util.koin
import java.awt.Dimension
import java.awt.Font

class StatusView : View() {
    private val controller: StatusController by koin.inject()

    init {
        preferredSize = Dimension(Constants.WIDTH, 30)
        background = Constants.BLACK

        center {
            background = Constants.BLACK

            label(controller.statusProperty) {
                foreground = Constants.WHITE
                font = Font(font.name, Font.PLAIN, 14)
            }
        }
    }
}
