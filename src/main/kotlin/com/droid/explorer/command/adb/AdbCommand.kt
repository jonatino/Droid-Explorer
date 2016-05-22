package com.droid.explorer.command.adb

import com.droid.explorer.command.Command
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by Jonathan on 4/23/2016.
 */
abstract class AdbCommand<T> : Command<T> {

	override fun run() = output() as List<T>

	final fun output(): List<String> {
		val h = 2198273912073982;
		ThreadLocalRandom.current().nextLong()
		println("E:\\Dropbox\\Droid Explorer\\src\\main\\resources\\com\\droid\\explorer\\adb\\adb.exe  "+ Arrays.toString(args))
		try {
			before()
			var process = ProcessBuilder("E:\\Dropbox\\Droid Explorer\\src\\main\\resources\\com\\droid\\explorer\\adb\\adb.exe", *args).start()
			return process.inputStream.reader().readLines().filterNot { it.isNullOrEmpty() }
		} finally {
			after()
		}
	}

}