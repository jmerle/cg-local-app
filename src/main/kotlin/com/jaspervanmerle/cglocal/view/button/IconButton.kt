package com.jaspervanmerle.cglocal.view.button

import com.jaspervanmerle.cglocal.Constants
import java.awt.AlphaComposite
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.Icon
import javax.swing.border.EmptyBorder

class IconButton(icon: Icon) : Button(icon) {
    private var alpha = 1.0f

    init {
        isContentAreaFilled = false
        border = EmptyBorder(0, 0, 0, 0)

        background = Constants.TRANSPARENT

        addChangeListener {
            alpha = if (model.isRollover) 0.7f else 1.0f
        }
    }

    override fun paintComponent(g: Graphics) {
        val g2 = g as Graphics2D
        g2.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)
        super.paintComponent(g2)
    }
}
