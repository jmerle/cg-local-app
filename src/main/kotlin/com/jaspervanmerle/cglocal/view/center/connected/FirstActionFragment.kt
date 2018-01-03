package com.jaspervanmerle.cglocal.view.center.connected

import com.jaspervanmerle.cglocal.Styles
import com.jaspervanmerle.cglocal.controller.ConnectedController
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*

class FirstActionFragment : Fragment() {
    val controller: ConnectedController by inject()

    override val root = vbox {
        style {
            spacing = 5.px
            padding = box(5.px)
            alignment = Pos.CENTER
        }

        val fileName = controller.selectedFile.name

        label("File '$fileName' selected") {
            style {
                fontSize = 16.px
                fontWeight = FontWeight.BOLD
            }
        }

        label("Choose your first action:")

        button("Download IDE to File") {
            addClass(Styles.orangeButton)

            disableProperty().bind(controller.firstActionDisabledProperty)

            action {
                controller.firstActionDownload()
            }
        }

        button("Upload File to IDE") {
            addClass(Styles.orangeButton)

            disableProperty().bind(controller.firstActionDisabledProperty)

            action {
                controller.firstActionUpload()
            }
        }

        button("Change file") {
            addClass(Styles.orangeButton)

            disableProperty().bind(controller.firstActionDisabledProperty)

            action {
                controller.firstActionChangeFile()
            }
        }
    }
}
