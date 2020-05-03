package com.jaspervanmerle.cglocal.view.button

import com.jaspervanmerle.cglocal.Constants
import javax.swing.Icon
import javax.swing.border.EmptyBorder

class IconButton(icon: Icon) : Button(icon) {
    init {
        isContentAreaFilled = false
        border = EmptyBorder(0, 0, 0, 0)

        background = Constants.TRANSPARENT
        foreground = Constants.WHITE
    }
}
