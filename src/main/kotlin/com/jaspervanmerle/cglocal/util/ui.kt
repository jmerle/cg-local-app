package com.jaspervanmerle.cglocal.util

import com.jaspervanmerle.cglocal.CGLocal
import com.jaspervanmerle.cglocal.controller.StatusController
import com.jaspervanmerle.cglocal.view.MainView
import mu.KotlinLogging
import java.awt.BorderLayout
import java.awt.Desktop
import java.net.URL
import javax.swing.JOptionPane
import javax.swing.JPanel
import kotlin.concurrent.thread
import kotlin.reflect.KClass
import kotlin.system.exitProcess

val logger = KotlinLogging.logger {}

fun <T : JPanel> setCenter(newCenter: KClass<T>, parent: KClass<*> = MainView::class) {
    if (CGLocal.stopping) {
        return
    }

    logger.info("Placing ${newCenter.simpleName} in the center of ${parent.simpleName}")

    val parentPanel = koin.get(parent) as JPanel
    val newCenterPanel = koin.get(newCenter) as JPanel

    val parentLayout = parentPanel.layout as BorderLayout
    val currentCenterPanel = parentLayout.getLayoutComponent(BorderLayout.CENTER)
    if (currentCenterPanel != null) {
        parentPanel.remove(currentCenterPanel)
        parentPanel.revalidate()
        parentPanel.repaint()
    }

    parentPanel.add(newCenterPanel, BorderLayout.CENTER)
    parentPanel.revalidate()
    parentPanel.repaint()
}

fun setStatus(status: String) {
    if (CGLocal.stopping) {
        return
    }

    logger.info("Changing status to \"$status\"")
    koin.get<StatusController>().status = status
}

fun openBrowser(url: String) {
    if (Desktop.isDesktopSupported()) {
        // On Linux, getDesktop().browse() hangs the application
        // This issue can be fixed by running the call in a separate thread
        thread {
            logger.info("Opening $url")
            Desktop.getDesktop().browse(URL(url).toURI())
        }
    }
}

fun errorAndExit(message: String) {
    JOptionPane.showMessageDialog(null, message, "CG Local", JOptionPane.ERROR_MESSAGE)
    exitProcess(1)
}
