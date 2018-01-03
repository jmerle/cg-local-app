package com.jaspervanmerle.cglocal.view

import com.jaspervanmerle.cglocal.Styles
import com.jaspervanmerle.cglocal.controller.StatusController
import tornadofx.*

class StatusView : View() {
    val controller: StatusController by inject()

    override val root = borderpane {
        center {
            style {
                backgroundColor += Styles.black
                minHeight = 30.px
            }

            label(controller.statusProperty) {
                style {
                    textFill = Styles.white
                    fontSize = 14.px
                }
            }
        }
    }
}
