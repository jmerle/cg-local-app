package com.jaspervanmerle.cglocal.util

import kotlin.reflect.KProperty

class ObservableProperty<T>(var value: T) {
    private val observers = mutableListOf<(newValue: T) -> Unit>()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, newValue: T) {
        val valueChanged = value != newValue

        value = newValue

        if (valueChanged) {
            for (observer in observers) {
                observer(newValue)
            }
        }
    }

    fun observe(observer: (newValue: T) -> Unit) {
        observers.add(observer)
    }
}
