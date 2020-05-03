package com.jaspervanmerle.cglocal.util

import java.awt.GridBagLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

fun JPanel.center(block: JPanel.() -> Unit) {
    layout = GridBagLayout()
    val content = JPanel()
    add(content)
    content.block()
}

fun JPanel.label(label: String, constraints: Any? = null, init: JLabel.() -> Unit = {}) =
    JLabel(label).apply(init).also {
        add(it, constraints)
    }

fun JPanel.label(label: ObservableProperty<String>, constraints: Any? = null, init: JLabel.() -> Unit = {}) =
    label(label.value, constraints, init).also { component ->
        label.observe {
            component.text = it
        }
    }

fun JPanel.button(label: String, constraints: Any? = null, init: JButton.() -> Unit = {}) =
    JButton(label).apply(init).also {
        add(it, constraints)
    }
