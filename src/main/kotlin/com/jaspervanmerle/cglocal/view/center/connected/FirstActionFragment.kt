package com.jaspervanmerle.cglocal.view.center.connected

import com.jaspervanmerle.cglocal.controller.ConnectedController
import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.view.View
import java.awt.Font

class FirstActionFragment : View("wrap 1, insets 5, align center", "align center") {
    private val connectedController: ConnectedController by koin.inject()

    init {
        val fileName = connectedController.selectedFile.name

        label("File '$fileName' selected") {
            font = font.deriveFont(Font.BOLD, 16f)
        }

        label("Choose your first action:") {
            font = font.deriveFont(Font.PLAIN, 14f)
        }

        button("Download IDE to file") {
            addActionListener {
                connectedController.firstActionDownload()
                isEnabled = false
            }
        }

        button("Upload file to IDE") {
            addActionListener {
                connectedController.firstActionUpload()
                isEnabled = false
            }
        }

        button("Change file") {
            addActionListener {
                connectedController.firstActionChangeFile()
                isEnabled = false
            }
        }
    }
}
