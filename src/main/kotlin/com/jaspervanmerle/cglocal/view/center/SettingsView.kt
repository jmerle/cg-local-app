package com.jaspervanmerle.cglocal.view.center

import com.jaspervanmerle.cglocal.CGLocal
import com.jaspervanmerle.cglocal.Config
import com.jaspervanmerle.cglocal.Styles
import com.jaspervanmerle.cglocal.controller.SettingsController
import javafx.event.EventTarget
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.layout.Priority
import javafx.scene.text.FontWeight
import tornadofx.*
import kotlin.reflect.KMutableProperty

class SettingsView : View() {
    val controller: SettingsController by inject()

    override val root = borderpane {
        center {
            vbox {
                style {
                    padding = box(10.px)
                    spacing = 10.px
                }

                options("Use one file for all puzzles", Config::oneFileForAllPuzzles, mapOf(
                    "Yes" to true,
                    "No" to false
                ))

                options("Auto Play", Config::autoPlay, mapOf(
                    "On" to true,
                    "Off" to false
                ))

                options("Enable 2-way data binding", Config::twoWayDataBinding, mapOf(
                    "Yes" to true,
                    "No" to false
                ))

                options("Default first action", Config::defaultAction, mapOf(
                    "Download" to "download",
                    "Ask me" to "ask",
                    "Upload" to "upload"
                ))
            }
        }

        bottom {
            vbox {
                style {
                    padding = box(5.px)
                    alignment = Pos.CENTER
                }

                label("Version") {
                    style {
                        fontWeight = FontWeight.BOLD
                    }
                }

                label(CGLocal.version)
            }
        }
    }

    fun <T> EventTarget.options(title: String, property: KMutableProperty<T>, options: Map<String, T>): Node {
        return vbox {
            style {
                spacing = 5.px
            }

            label(title) {
                style {
                    fontSize = 16.px
                    fontWeight = FontWeight.BOLD
                }
            }

            hbox {
                togglegroup {
                    for ((name, value) in options) {
                        radiobutton(name, value = value) {
                            isSelected = property.getter.call() == value

                            addClass(Styles.orangeButton)
                            addClass(Styles.settingsButton)

                            style(true) {
                                styleClass -= "radio-button"

                                hgrow = Priority.ALWAYS
                                minWidth = 0.px
                                maxWidth = Double.MAX_VALUE.px
                            }

                            action {
                                property.setter.call(value)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDock() {
        controller.setSettingsOpened()
    }

    override fun onUndock() {
        controller.setSettingsClosed()
    }
}
