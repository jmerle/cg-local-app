package com.jaspervanmerle.cglocal.view.center

import com.jaspervanmerle.cglocal.util.openBrowser
import com.jaspervanmerle.cglocal.view.View
import java.awt.Desktop
import java.awt.Font

class DisconnectedView : View("wrap 1, insets 20, align center", "align center") {
    init {
        label("Please connect via the CodinGame IDE") {
            font = Font(font.name, Font.BOLD, 16)
        }

        if (Desktop.isDesktopSupported()) {
            button("Open CodinGame") {
                addActionListener {
                    openBrowser("https://www.codingame.com/")
                }
            }
        }
    }
}
