package com.jaspervanmerle.cglocal.view.center.connected

import com.jaspervanmerle.cglocal.Styles
import com.jaspervanmerle.cglocal.controller.ConnectedController
import com.jaspervanmerle.cglocal.server.Server
import com.jaspervanmerle.cglocal.util.icon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*

class ConnectedMenuView : View() {
    val controller: ConnectedController by inject()

    override val root = borderpane {
        style {
            minHeight = 50.px
            backgroundColor += Styles.orange
            padding = box(0.px, 10.px, 0.px, 10.px)
        }

        left {
            hbox {
                alignment = Pos.CENTER_LEFT

                label(controller.titleProperty) {
                    style {
                        textFill = Styles.black
                        fontSize = 20.px
                        fontWeight = FontWeight.BOLD
                    }
                }
            }
        }

        right {
            hbox {
                alignment = Pos.CENTER_LEFT

                button {
                    addClass(Styles.iconButton)

                    style(true) {
                        backgroundColor += Styles.orange
                    }

                    graphic = icon(FontAwesomeIcon.CLOSE) {
                        glyphSize = 20
                        fill = Styles.black
                    }

                    action {
                        Server.connectedSocket?.close()
                    }
                }
            }
        }
    }
}
