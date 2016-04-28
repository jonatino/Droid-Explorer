package com.droid.explorer.saving

import java.util.*

/**
 * Created by Jonathan on 4/28/2016.
 */
object Prefs {

	val savedPrefs = HashMap<String, Any>()

	operator inline fun <reified T> get(key: String, defaultValue: T) = savedPrefs.getOrDefault(key, defaultValue)

	operator inline fun <reified T> set(key: String, value: T) = savedPrefs.put(key, value!!)

}