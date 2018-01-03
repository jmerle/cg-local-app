package com.jaspervanmerle.cglocal.view

import com.jaspervanmerle.cglocal.Styles
import com.jaspervanmerle.cglocal.controller.SettingsController
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*

class MenuView : View() {
    val settingsController: SettingsController by inject()

    override val root = borderpane {
        style {
            minHeight = 50.px
            backgroundColor += Styles.black
            padding = box(0.px, 10.px, 0.px, 10.px)
        }

        left {
            hbox {
                alignment = Pos.CENTER_LEFT

                label("CG") {
                    style {
                        textFill = Styles.white
                        fontSize = 26.px
                        fontWeight = FontWeight.BOLD
                    }
                }

                label("Local") {
                    style {
                        textFill = Styles.orange
                        fontSize = 26.px
                        fontWeight = FontWeight.BOLD
                    }
                }
            }
        }

        right {
            hbox {
                alignment = Pos.CENTER_RIGHT

                button {
                    addClass(Styles.iconButton)

                    style(true) {
                        backgroundColor += Styles.black
                    }

                    graphicProperty().bind(settingsController.iconProperty)

                    action {
                        settingsController.toggleSettings()
                    }
                }
            }
        }
    }
}
