package com.jaspervanmerle.cglocal.util

import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

fun frame(title: String, init: JFrame.() -> Unit = {}) = JFrame(title).apply(init)
fun panel(init: JPanel.() -> Unit = {}) = JPanel().apply(init)

fun JPanel.label(label: String, constraints: Any? = null, init: JLabel.() -> Unit = {}) =
    JLabel(label).apply(init).also {
        add(it, constraints)
    }

fun JPanel.label(label: ObservableProperty<String>, constraints: Any? = null, init: JLabel.() -> Unit = {}) =
    label(label.value, constraints).also { component ->
        label.observe {
            component.text = it
        }
    }

fun JPanel.button(label: String, constraints: Any? = null, init: JButton.() -> Unit = {}) =
    JButton(label).apply(init).also {
        add(it, constraints)
    }
