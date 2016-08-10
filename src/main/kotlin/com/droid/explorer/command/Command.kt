package com.droid.explorer.command

/**
 * Created by Jonathan on 8/10/2016.
 */
interface Command {

    val args: Array<String>

    fun run(block: ((String) -> Unit)? = null)

    fun before() {

    }

    fun after() {

    }

}