package com.droid.explorer.command.adb

import com.droid.explorer.command.Command

/**
 * Created by Jonathan on 4/23/2016.
 */
abstract class AdbCommand : Command {

	override inline operator final fun invoke(action: (List<String>) -> Any) {
		var process = ProcessBuilder("E:\\Dropbox\\Droid Explorer\\src\\main\\resources\\adb.exe", *args).start()
		val lines = process.inputStream.reader().readLines().filterNot { it.isNullOrEmpty() }
		action(lines)
	}

}