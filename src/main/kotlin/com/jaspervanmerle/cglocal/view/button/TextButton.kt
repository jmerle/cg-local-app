package com.jaspervanmerle.cglocal.view.button

import com.jaspervanmerle.cglocal.Constants
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import javax.swing.AbstractButton
import javax.swing.plaf.metal.MetalButtonUI
import kotlin.math.min
import kotlin.math.round

class TextButton(text: String) : Button(text) {
    init {
        preferredSize = Dimension(200, 40)

        background = Constants.ORANGE
        foreground = Constants.BLACK

        font = Font(font.name, Font.BOLD, 14)

        ui = object : MetalButtonUI() {
            override fun paintButtonPressed(g: Graphics?, b: AbstractButton?) {
                // Do nothing
            }
        }

        addChangeListener {
            background = if (model.isRollover) {
                Constants.ORANGE.manipulate(0.9f)
            } else {
                Constants.ORANGE
            }
        }
    }

    private fun Color.manipulate(factor: Float): Color {
        val newRed = min(round(red * factor).toInt(), 255)
        val newGreen = min(round(green * factor).toInt(), 255)
        val newBlue = min(round(blue * factor).toInt(), 255)
        return Color(newRed, newGreen, newBlue, alpha)
    }
}
