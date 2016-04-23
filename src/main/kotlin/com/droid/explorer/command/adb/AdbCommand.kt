package com.droid.explorer.command.adb

import com.droid.explorer.command.Command

/**
 * Created by Jonathan on 4/23/2016.
 */
abstract class AdbCommand<T> : Command<T> {

	override operator fun invoke(action: (T) -> Any) = (output() as List<T>).forEach { action(it) }

	final fun output(): List<String> {
		var process = ProcessBuilder("adb.exe", *args).start()
		return process.inputStream.reader().readLines().filterNot { it.isNullOrEmpty() }
	}

}