package com.jaspervanmerle.cglocal

import com.jaspervanmerle.cglocal.controller.ConnectedController
import com.jaspervanmerle.cglocal.controller.MainController
import com.jaspervanmerle.cglocal.controller.SettingsController
import com.jaspervanmerle.cglocal.controller.StatusController
import com.jaspervanmerle.cglocal.server.Server
import com.jaspervanmerle.cglocal.util.koin
import com.jaspervanmerle.cglocal.view.MainView
import com.jaspervanmerle.cglocal.view.MenuView
import com.jaspervanmerle.cglocal.view.StatusView
import com.jaspervanmerle.cglocal.view.center.ConnectedView
import com.jaspervanmerle.cglocal.view.center.DisconnectedView
import com.jaspervanmerle.cglocal.view.center.SettingsView
import com.jaspervanmerle.cglocal.view.center.connected.ConnectedMenuView
import com.jaspervanmerle.cglocal.view.center.connected.FirstActionFragment
import com.jaspervanmerle.cglocal.view.center.connected.SelectFileFragment
import com.jaspervanmerle.cglocal.view.center.connected.SetupCompletedFragment
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun main() {
    val module = module {
        single { CGLocal() }
        single { Server() }
        single { Config() }

        single { MainController() }
        single { StatusController() }
        single { ConnectedController() }
        single { SettingsController() }

        single { MainView() }
        single { MenuView() }
        single { StatusView() }
        single { ConnectedView() }
        single { DisconnectedView() }
        single { SettingsView() }
        single { ConnectedMenuView() }

        factory { FirstActionFragment() }
        factory { SelectFileFragment() }
        factory { SetupCompletedFragment() }
    }

    startKoin {
        modules(module)
    }

    koin.get<CGLocal>().start()
}
