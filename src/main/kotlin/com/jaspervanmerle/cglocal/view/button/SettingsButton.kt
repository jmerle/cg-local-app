package com.jaspervanmerle.cglocal.view.button

import com.jaspervanmerle.cglocal.Constants
import java.awt.Cursor
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.AbstractButton
import javax.swing.JToggleButton
import javax.swing.plaf.metal.MetalButtonUI

class SettingsButton(text: String) : JToggleButton(text) {
    init {
        preferredSize = Dimension(200, 40)

        isBorderPainted = false
        isFocusPainted = false

        background = Constants.GRAY
        foreground = Constants.BLACK

        font = font.deriveFont(Font.BOLD, 14f)

        ui = object : MetalButtonUI() {
            override fun paintButtonPressed(g: Graphics?, b: AbstractButton?) {
                // Do nothing
            }
        }

        addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent?) {
                cursor = if (model.isSelected) {
                    Cursor.getDefaultCursor()
                } else {
                    Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                }
            }

            override fun mouseExited(e: MouseEvent?) {
                cursor = Cursor.getDefaultCursor()
            }
        })

        addChangeListener {
            if (model.isSelected) {
                background = Constants.BLACK
                foreground = Constants.ORANGE
            } else {
                background = Constants.GRAY
                foreground = Constants.BLACK
            }
        }
    }
}
