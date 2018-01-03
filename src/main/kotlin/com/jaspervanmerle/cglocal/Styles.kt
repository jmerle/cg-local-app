package com.jaspervanmerle.cglocal

import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.effect.Effect
import javafx.scene.effect.Shadow
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val iconButton by cssclass()
        val orangeButton by cssclass()
        val settingsButton by cssclass()

        val black = c("#252e38")
        val orange = c("#f2bb13")
        val white = c("#ffffff")
    }

    init {
        s(label) {
            fontSize = 14.px
            textFill = black
            wrapText = true
            textAlignment = TextAlignment.CENTER
        }

        iconButton {
            padding = box(0.px)
            cursor = Cursor.HAND

            and(hover) {
                opacity = 0.7
            }
        }

        orangeButton {
            backgroundColor += orange
            backgroundRadius += box(0.px)

            minWidth = 200.px
            maxWidth = 200.px
            minHeight = 40.px

            textFill = black
            fontSize = 14.px
            fontWeight = FontWeight.BOLD

            wrapText = true
            textAlignment = TextAlignment.CENTER

            cursor = Cursor.HAND

            and(hover) {
                opacity = 0.8
            }
        }

        settingsButton {
            backgroundColor += c(69, 76, 85, 0.08)
            alignment = Pos.CENTER

            and(selected) {
                backgroundColor += black
                textFill = orange
                opacity = 1.0

                cursor = Cursor.DEFAULT
            }

            and(hover) {
                opacity = 1.0
            }
        }
    }
}
