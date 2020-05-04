package com.jaspervanmerle.cglocal.view.button

import java.awt.Cursor
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
