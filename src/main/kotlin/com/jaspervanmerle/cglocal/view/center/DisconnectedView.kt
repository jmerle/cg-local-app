package com.jaspervanmerle.cglocal.view.center

import com.jaspervanmerle.cglocal.view.View
import mu.KLogging
import java.awt.Desktop
import java.awt.Font
import java.net.URL
import kotlin.concurrent.thread

class DisconnectedView : View("wrap 1, insets 20, align center", "align center") {
    companion object : KLogging()

    init {
        label("Please connect via the CodinGame IDE") {
            font = font.deriveFont(Font.BOLD, 16f)
        }

        if (Desktop.isDesktopSupported()) {
            button("Open CodinGame") {
                addActionListener {
                    val url = "https://www.codingame.com/"

                    // On Linux Desktop.getDesktop().browse() hangs the application
                    // This issue can be fixed by running the call in a separate thread
                    thread {
                        logger.info("Opening '$url'")
                        Desktop.getDesktop().browse(URL(url).toURI())
                    }
                }
            }
        }
    }
}
