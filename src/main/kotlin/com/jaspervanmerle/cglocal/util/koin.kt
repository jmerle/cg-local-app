package com.jaspervanmerle.cglocal.util

import org.koin.core.context.KoinContextHandler

/**
 * This may not be the way Koin is supposed to be used,
 * but it makes it easier to transition from TornadoFX.
 */
val koin by lazy { KoinContextHandler.get() }
