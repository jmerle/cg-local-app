package com.jaspervanmerle.cglocal.view.center

import com.jaspervanmerle.cglocal.util.label
import java.awt.BorderLayout
import javax.swing.JPanel

class ConnectedView : JPanel() {
    init {
        layout = BorderLayout()

        label("Connected", BorderLayout.NORTH)
    }
}
