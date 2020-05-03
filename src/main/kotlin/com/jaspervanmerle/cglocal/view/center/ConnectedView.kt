package com.jaspervanmerle.cglocal.view.center

import com.jaspervanmerle.cglocal.view.View
import java.awt.BorderLayout

class ConnectedView : View() {
    init {
        layout = BorderLayout()

        label("Connected", BorderLayout.NORTH)
    }
}
