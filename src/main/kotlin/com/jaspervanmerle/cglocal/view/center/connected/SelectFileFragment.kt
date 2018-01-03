package com.jaspervanmerle.cglocal.view.center.connected

import com.jaspervanmerle.cglocal.Config
import com.jaspervanmerle.cglocal.Styles
import com.jaspervanmerle.cglocal.controller.ConnectedController
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*

class SelectFileFragment : Fragment() {
    val controller: ConnectedController by inject()

    override val root = vbox {
        style {
            spacing = 5.px
            padding = box(5.px)
            alignment = Pos.CENTER
        }

        label("Please select a file to start") {
            style {
                fontSize = 16.px
                fontWeight = FontWeight.BOLD
            }
        }

        if (Config.isPreviouslySelected(controller.questionId)) {
            val path = Config.getPreviouslySelectedFile(controller.questionId)

            label("Previously selected file:\n$path") {
                style {
                    fontSize = 12.px
                }
            }

            button("Use previously selected file") {
                addClass(Styles.orangeButton)

                style(true) {
                    fontSize = 13.px
                }

                action {
                    controller.usePreviouslySelectedFile()
                }
            }
        }

        button("Select a file") {
            addClass(Styles.orangeButton)

            style(true) {
                fontSize = 13.px
            }

            action {
                controller.selectFile()
            }
        }
    }
}
