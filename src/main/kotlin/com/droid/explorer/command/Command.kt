package com.droid.explorer.command

/**
 * Created by Jonathan on 4/23/2016.
 */
interface Command<T> {

	val args: Array<String>

	fun run(): List<T>

	fun before() {

	}

	fun after() {

	}

}