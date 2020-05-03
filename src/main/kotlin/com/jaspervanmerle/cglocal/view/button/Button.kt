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

    init {
        isBorderPainted = false
        isFocusPainted = false

        addChangeListener {
            cursor = if (model.isRollover) {
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            } else {
                Cursor.getDefaultCursor()
            }
        }
    }
}
