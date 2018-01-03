package com.jaspervanmerle.cglocal.controller

import com.jaspervanmerle.cglocal.Styles
import com.jaspervanmerle.cglocal.server.Server
import com.jaspervanmerle.cglocal.util.icon
import com.jaspervanmerle.cglocal.util.setCenter
import com.jaspervanmerle.cglocal.view.center.ConnectedView
import com.jaspervanmerle.cglocal.view.center.DisconnectedView
import com.jaspervanmerle.cglocal.view.center.SettingsView
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*

class SettingsController : Controller() {
    val iconProperty = SimpleObjectProperty<FontAwesomeIconView>()
    var icon by iconProperty

    val closedIcon = icon(FontAwesomeIcon.GEAR) {
        glyphSize = 26
        fill = Styles.white
    }

    val openedIcon = icon(FontAwesomeIcon.HOME) {
        glyphSize = 26
        fill = Styles.white
    }

    var settingsClosed = true

    init {
        icon = closedIcon
    }

    fun toggleSettings() {
        if (settingsClosed) {
            setCenter(SettingsView::class)
        } else {
            if (Server.connected) {
                setCenter(ConnectedView::class)
            } else {
                setCenter(DisconnectedView::class)
            }
        }
    }

    fun setSettingsOpened() {
        icon = openedIcon
        settingsClosed = false
    }

    fun setSettingsClosed() {
        icon = closedIcon
        settingsClosed = true
    }
}
