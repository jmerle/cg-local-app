package com.jaspervanmerle.cglocal.view.button

import com.jaspervanmerle.cglocal.Constants
import java.awt.Dimension
import java.awt.Font

class TextButton(text: String) : Button(text) {
    init {
        preferredSize = Dimension(200, 40)

        background = Constants.ORANGE
        foreground = Constants.BLACK

        font = Font(font.name, Font.BOLD, 14)
    }
}
