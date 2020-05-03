package com.jaspervanmerle.cglocal.view.button

import java.awt.AlphaComposite
import java.awt.Cursor
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.Icon
import javax.swing.JButton

abstract class Button : JButton {
    constructor(text: String) : super(text)
    constructor(icon: Icon) : super(icon)

    private var alpha = 1.0f

    init {
        isBorderPainted = false
        isFocusPainted = false

        addChangeListener {
            if (model.isRollover) {
                alpha = 0.7f
                cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            } else {
                alpha = 1.0f
                cursor = Cursor.getDefaultCursor()
            }
        }
    }

    override fun paintComponent(g: Graphics) {
        val g2 = g as Graphics2D
        g2.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)
        super.paintComponent(g2)
    }
}
