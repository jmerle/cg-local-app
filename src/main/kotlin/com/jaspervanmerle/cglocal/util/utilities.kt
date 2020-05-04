package com.jaspervanmerle.cglocal.util

import com.jaspervanmerle.cglocal.CGLocal
import com.jaspervanmerle.cglocal.controller.StatusController
import com.jaspervanmerle.cglocal.view.MainView
import com.jaspervanmerle.cglocal.view.View
import mu.KotlinLogging
import org.koin.core.context.KoinContextHandler
import java.awt.BorderLayout
import java.awt.Color
import javax.swing.JOptionPane
import javax.swing.JPanel
import kotlin.math.min
import kotlin.math.round
import kotlin.reflect.KClass
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

val koin by lazy { KoinContextHandler.get() }

fun <T : View> setCenter(newCenter: KClass<T>, parent: KClass<*> = MainView::class) {
    if (CGLocal.stopping) {
        return
    }

    logger.info("Placing ${newCenter.simpleName} in the center of ${parent.simpleName}")

    val parentPanel = koin.get(parent) as JPanel
    val newCenterPanel = koin.get(newCenter) as View

    val parentLayout = parentPanel.layout as BorderLayout
    val currentCenterPanel = parentLayout.getLayoutComponent(BorderLayout.CENTER)
    if (currentCenterPanel != null) {
        parentPanel.remove(currentCenterPanel)
        parentPanel.revalidate()
        parentPanel.repaint()

        if (currentCenterPanel is View) {
            currentCenterPanel.onHide()
        }
    }

    parentPanel.add(newCenterPanel, BorderLayout.CENTER)
    parentPanel.revalidate()
    parentPanel.repaint()

    newCenterPanel.onShow()
}

fun setStatus(status: String) {
    if (CGLocal.stopping) {
        return
    }

    logger.info("Changing status to '$status'")
    koin.get<StatusController>().status = status
}

fun errorAndExit(message: String) {
    val formattedMessage = "<html><center>$message</center></html>"

    logger.error(message.substringBefore("."))
    JOptionPane.showMessageDialog(null, formattedMessage, "CG Local", JOptionPane.ERROR_MESSAGE)

    exitProcess(1)
}

fun Color.manipulate(factor: Float): Color {
    val newRed = min(round(red * factor).toInt(), 255)
    val newGreen = min(round(green * factor).toInt(), 255)
    val newBlue = min(round(blue * factor).toInt(), 255)
    return Color(newRed, newGreen, newBlue, alpha)
}
