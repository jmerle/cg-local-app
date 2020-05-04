package com.jaspervanmerle.cglocal.view.center.connected

import com.jaspervanmerle.cglocal.Config
import com.jaspervanmerle.cglocal.controller.ConnectedController
import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.view.View
import java.awt.Font

class SelectFileFragment : View("wrap 1, insets 5, align center", "align center") {
    private val connectedController: ConnectedController by koin.inject()
    private val config: Config by koin.inject()

    init {
        label("Please select a file to start") {
            font = font.deriveFont(Font.BOLD, 16f)
        }

        if (config.isPreviouslySelected(connectedController.questionId)) {
            val path = config.getPreviouslySelectedFile(connectedController.questionId)
            val formattedPath = if (path.length > 50) "…" + path.takeLast(50) else path

            label("Previously selected file:<br>$formattedPath") {
                font = font.deriveFont(Font.PLAIN, 12f)
            }

            button("Use previously selected file") {
                addActionListener {
                    connectedController.usePreviouslySelectedFile()
                }
            }
        }

        button("Select a file") {
            addActionListener {
                connectedController.selectFile()
            }
        }
    }
}
