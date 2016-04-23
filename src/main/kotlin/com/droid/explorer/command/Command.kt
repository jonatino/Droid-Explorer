package com.droid.explorer.command

/**
 * Created by Jonathan on 4/23/2016.
 */
interface Command {

	val args: Array<String>

	operator fun invoke(action: (List<String>) -> Any)
}