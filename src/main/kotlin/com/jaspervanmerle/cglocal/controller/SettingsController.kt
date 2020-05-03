package com.jaspervanmerle.cglocal.controller

import com.jaspervanmerle.cglocal.Constants
import com.jaspervanmerle.cglocal.server.Server
import com.jaspervanmerle.cglocal.util.ObservableProperty
import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.util.setCenter
import com.jaspervanmerle.cglocal.view.center.ConnectedView
import com.jaspervanmerle.cglocal.view.center.DisconnectedView
import com.jaspervanmerle.cglocal.view.center.SettingsView
import jiconfont.icons.font_awesome.FontAwesome
import jiconfont.swing.IconFontSwing
import javax.swing.Icon

class SettingsController {
    private val server: Server by koin.inject()

    private val closedIcon = IconFontSwing.buildIcon(FontAwesome.COG, 26.0f, Constants.WHITE)
    private val openedIcon = IconFontSwing.buildIcon(FontAwesome.HOME, 26.0f, Constants.WHITE)

    val iconProperty = ObservableProperty<Icon>(closedIcon)
    private var icon by iconProperty

    private var settingsClosed = true

    init {
        icon = closedIcon
    }

    fun toggleSettings() {
        if (settingsClosed) {
            setCenter(SettingsView::class)
        } else {
            if (server.connected) {
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
