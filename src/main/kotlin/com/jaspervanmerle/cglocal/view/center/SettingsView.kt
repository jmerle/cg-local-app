package com.jaspervanmerle.cglocal.view.center

import com.jaspervanmerle.cglocal.Config
import com.jaspervanmerle.cglocal.controller.SettingsController
import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.view.View
import com.jaspervanmerle.cglocal.view.button.SettingsButton
import net.miginfocom.swing.MigLayout
import java.awt.Font
import javax.swing.ButtonGroup
import javax.swing.JPanel
import kotlin.reflect.KMutableProperty

class SettingsView : View("wrap 1, insets 10") {
    private val settingsController: SettingsController by koin.inject()
    private val config: Config by koin.inject()

    init {
        options("Use one file for all puzzles", config::oneFileForAllPuzzles, mapOf(
            "Yes" to true,
            "No" to false
        ))

        options("Auto Play", config::autoPlay, mapOf(
            "On" to true,
            "Off" to false
        ))

        options("Enable 2-way data binding", config::twoWayDataBinding, mapOf(
            "Yes" to true,
            "No" to false
        ))

        options("Default first action", config::defaultAction, mapOf(
            "Download" to "download",
            "Ask me" to "ask",
            "Upload" to "upload"
        ))
    }

    override fun onShow() {
        settingsController.setSettingsOpened()
    }

    override fun onHide() {
        settingsController.setSettingsClosed()
    }

    private fun <T> JPanel.options(title: String, property: KMutableProperty<T>, options: Map<String, T>) {
        label(title) {
            font = font.deriveFont(Font.BOLD, 16f)
        }

        val buttonGroup = ButtonGroup()
        val buttonPanel = JPanel(MigLayout("insets 0", "[grow]0", "[grow]0"))

        val currentValue = property.getter.call()

        for ((label, value) in options) {
            val button = SettingsButton(label).apply {
                addActionListener {
                    property.setter.call(value)
                }
            }

            button.isSelected = value == currentValue

            buttonGroup.add(button)
            buttonPanel.add(button)
        }

        add(buttonPanel)
    }
}
