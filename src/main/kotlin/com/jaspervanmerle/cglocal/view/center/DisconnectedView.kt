package com.jaspervanmerle.cglocal.view.center

import com.jaspervanmerle.cglocal.Styles
import com.jaspervanmerle.cglocal.util.openBrowser
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*

class DisconnectedView : View() {
    override val root = vbox {
        style {
            padding = box(20.px)
            spacing = 10.px
            alignment = Pos.CENTER
        }

        label("Please connect via the CodinGame IDE") {
            style {
                fontSize = 16.px
                fontWeight = FontWeight.BOLD
            }
        }

        button("Open CodinGame") {
            addClass(Styles.orangeButton)

            action {
                openBrowser("https://www.codingame.com/")
            }
        }
    }
}
