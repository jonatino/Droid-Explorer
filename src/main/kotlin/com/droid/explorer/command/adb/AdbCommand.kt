package com.droid.explorer.command.adb

import com.droid.explorer.command.Command
import java.util.*

/**
 * Created by Jonathan on 4/23/2016.
 */
abstract class AdbCommand<T> : Command<T> {

	override fun callback(action: (T) -> Any) = (output() as List<T>).forEach { action(it) }

	final fun output(): List<String> {
		println("E:\\Dropbox\\Droid Explorer\\src\\main\\resources\\com\\droid\\explorer\\adb\\adb.exe  "+ Arrays.toString(args))
		var process = ProcessBuilder("E:\\Dropbox\\Droid Explorer\\src\\main\\resources\\com\\droid\\explorer\\adb\\adb.exe", *args).start()
		return process.inputStream.reader().readLines().filterNot { it.isNullOrEmpty() }
	}

}