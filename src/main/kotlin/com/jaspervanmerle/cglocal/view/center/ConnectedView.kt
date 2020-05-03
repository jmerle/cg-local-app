package com.jaspervanmerle.cglocal.view.center

import com.jaspervanmerle.cglocal.view.View
import java.awt.BorderLayout

class ConnectedView : View(BorderLayout()) {
    init {
        label("Connected", BorderLayout.CENTER)
    }
}
