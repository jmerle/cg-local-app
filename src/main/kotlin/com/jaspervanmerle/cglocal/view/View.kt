package com.jaspervanmerle.cglocal.view

import com.jaspervanmerle.cglocal.Constants
import com.jaspervanmerle.cglocal.util.ObservableProperty
import com.jaspervanmerle.cglocal.view.button.IconButton
import com.jaspervanmerle.cglocal.view.button.TextButton
import net.miginfocom.swing.MigLayout
import java.awt.Color
import java.awt.LayoutManager
import javax.swing.Icon
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

abstract class View : JPanel {
    constructor(layoutManager: LayoutManager) : super(layoutManager)

    constructor(layoutConstraints: String = "", columnConstraints: String = "", rowConstraints: String = "")
        : this(MigLayout(layoutConstraints, columnConstraints, rowConstraints))

    open fun onShow() {
        // Called when the view is placed in the center with setCenter()
    }

    open fun onHide() {
        // Called when the view is removed from the center with setCenter()
    }

    protected fun JPanel.label(text: String, constraints: Any? = null, init: JLabel.() -> Unit = {}): JLabel {
        return JLabel("<html><center>$text</center></html>")
            .apply {
                foreground = Constants.BLACK
            }
            .also(init)
            .also {
                add(it, constraints)
            }
    }

    protected fun JPanel.label(text: ObservableProperty<String>, constraints: Any? = null, init: JLabel.() -> Unit = {}): JLabel {
        return label(text.value, constraints, init).also { component ->
            text.observe {
                component.text = it
            }
        }
    }

    protected fun JPanel.button(text: String, constraints: Any? = null, init: JButton.() -> Unit = {}): JButton {
        return TextButton(text).apply(init).also {
            add(it, constraints)
        }
    }

    protected fun JPanel.button(icon: Icon, constraints: Any? = null, init: JButton.() -> Unit = {}): JButton {
        return IconButton(icon).apply(init).also {
            add(it, constraints)
        }
    }

    protected fun JPanel.button(prop: ObservableProperty<*>, constraints: Any? = null, init: JButton.() -> Unit = {}): JButton {
        return if (prop.value is String) {
            button(prop.value as String, constraints, init).also { component ->
                prop.observe {
                    component.text = it as String
                }
            }
        } else {
            button(prop.value as Icon, constraints, init).also { component ->
                prop.observe {
                    component.icon = it as Icon
                }
            }
        }
    }

    protected fun String.color(color: Color): String {
        val hex = String.format("#%02x%02x%02x", color.red, color.green, color.blue)
        return "<font color='$hex'>$this</font>"
    }
}
