package com.jaspervanmerle.cglocal.view.button

import com.jaspervanmerle.cglocal.Constants
import com.jaspervanmerle.cglocal.util.manipulate
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import javax.swing.AbstractButton
import javax.swing.plaf.metal.MetalButtonUI

class TextButton(text: String) : Button(text) {
    init {
        preferredSize = Dimension(200, 40)

        background = Constants.ORANGE
        foreground = Constants.BLACK

        font = font.deriveFont(Font.BOLD, 14f)

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
}
