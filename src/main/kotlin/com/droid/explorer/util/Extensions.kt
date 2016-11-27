package com.droid.explorer.util

import javafx.scene.control.ButtonBase
import javafx.scene.control.TableView
import kotlin.reflect.KProperty

/**
 * Created by Jonathan on 8/10/2016.
 */
fun <T> TableView<T>.withSelected(block: (T) -> Unit) = selectionModel.selectedItems.forEach { block(it) }

fun <T> TableView<T>.selectedItems() = selectionModel.selectedItems

fun ButtonBase.onAction(block: () -> Unit) = setOnAction {
    block()
}

var ButtonBase.action by BB()

class BB {

    lateinit var value: () -> Unit

    operator fun getValue(thisRef: Any?, property: KProperty<*>) = value

    operator fun setValue(thisRef: ButtonBase, property: KProperty<*>, value: () -> Unit) {
        thisRef.onAction(value)
        this.value = value
    }

}