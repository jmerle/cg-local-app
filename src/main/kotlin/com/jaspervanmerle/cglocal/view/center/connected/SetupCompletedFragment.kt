package com.jaspervanmerle.cglocal.view.center.connected

import com.jaspervanmerle.cglocal.Styles
import com.jaspervanmerle.cglocal.controller.ConnectedController
import com.jaspervanmerle.cglocal.util.icon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*

class SetupCompletedFragment : Fragment() {
    val controller: ConnectedController by inject()

    override val root = vbox {
        style {
            spacing = 10.px
            padding = box(5.px)
            alignment = Pos.CENTER
        }

        label {
            graphic = icon(FontAwesomeIcon.CODE) {
                glyphSize = 100
                fill = Styles.black
            }
        }

        label("Synchronizing '${controller.selectedFile.name}'") {
            style {
                fontSize = 20.px
                fontWeight = FontWeight.BOLD
            }
        }

        button("Force download") {
            addClass(Styles.orangeButton)

            action {
                controller.forceDownload()
            }
        }

        button("Force upload") {
            addClass(Styles.orangeButton)

            action {
                controller.forceUpload()
            }
        }
    }
}
