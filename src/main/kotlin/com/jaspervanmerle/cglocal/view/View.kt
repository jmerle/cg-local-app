package com.jaspervanmerle.cglocal.view

import com.jaspervanmerle.cglocal.util.ObservableProperty
import com.jaspervanmerle.cglocal.view.button.IconButton
import java.awt.Color
import java.awt.GridBagLayout
import javax.swing.Icon
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

abstract class View : JPanel() {
    protected fun JPanel.center(block: JPanel.() -> Unit) {
        layout = GridBagLayout()
        val content = JPanel()
        add(content)
        content.block()
    }

    protected fun JPanel.label(label: String, constraints: Any? = null, init: JLabel.() -> Unit = {}): JLabel {
        val formattedLabel = if ("</font>" in label) {
            "<html>$label</html>"
        } else {
            label
        }

        return JLabel(formattedLabel).apply(init).also {
            add(it, constraints)
        }
    }

    protected fun JPanel.label(label: ObservableProperty<String>, constraints: Any? = null, init: JLabel.() -> Unit = {}): JLabel {
        return label(label.value, constraints, init).also { component ->
            label.observe {
                component.text = it
            }
        }
    }

    protected fun JPanel.button(icon: Icon, constraints: Any? = null, init: JButton.() -> Unit = {}): JButton {
        return IconButton(icon).apply(init).also {
            add(it, constraints)
        }
    }

    protected fun JPanel.button(icon: ObservableProperty<Icon>, constraints: Any? = null, init: JButton.() -> Unit = {}): JButton {
        return button(icon.value, constraints, init).also { component ->
            icon.observe {
                component.icon = it
            }
        }
    }

    protected fun String.color(color: Color): String {
        val hex = String.format("#%02x%02x%02x", color.red, color.green, color.blue)
        return "<font color='$hex'>$this</font>"
    }
}
