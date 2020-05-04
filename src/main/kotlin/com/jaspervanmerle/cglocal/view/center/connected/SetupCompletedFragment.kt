package com.jaspervanmerle.cglocal.view.center.connected

import com.jaspervanmerle.cglocal.Constants
import com.jaspervanmerle.cglocal.controller.ConnectedController
import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.view.View
import jiconfont.icons.font_awesome.FontAwesome
import jiconfont.swing.IconFontSwing
import java.awt.Font

class SetupCompletedFragment : View("wrap 1, insets 5, align center", "align center") {
    private val connectedController: ConnectedController by koin.inject()

    init {
        label(IconFontSwing.buildIcon(FontAwesome.CODE, 100f, Constants.BLACK))

        label("Synchronizing '${connectedController.selectedFile.name}'") {
            font = font.deriveFont(Font.BOLD, 20f)
        }

        button("Force download") {
            addActionListener {
                connectedController.forceDownload()
            }
        }

        button("Force upload") {
            addActionListener {
                connectedController.forceUpload()
            }
        }
    }
}
