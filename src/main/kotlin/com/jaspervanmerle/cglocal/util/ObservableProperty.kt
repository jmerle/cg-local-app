package com.jaspervanmerle.cglocal.util

import kotlin.reflect.KProperty

class ObservableProperty<T>(var value: T) {
    private val observers = mutableListOf<(newValue: T) -> Unit>()

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, newValue: T) {
        val valueChanged = value != newValue

        println("$value -> $newValue")
        value = newValue

        if (valueChanged) {
            println(observers.size)
            for (observer in observers) {
                observer(newValue)
            }
        }
    }

    fun observe(listener: (newValue: T) -> Unit) {
        observers += listener
    }
}
