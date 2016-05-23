package com.droid.explorer.command.adb

import com.droid.explorer.command.shell.impl.Start
import java.io.IOException
import java.net.Socket

/**
 * Created by Jonathan on 4/23/2016.
 */
abstract class AdbCommand<T> {

	abstract val args: Array<String>

	private val process = "E:\\Dropbox\\Droid Explorer\\src\\main\\resources\\com\\droid\\explorer\\adb\\adb.exe"

	fun run(block: ((String) -> Unit)? = null) {
		//println("E:\\Dropbox\\Droid Explorer\\src\\main\\resources\\com\\droid\\explorer\\adb\\adb.exe  " + Arrays.toString(args))
		try {
			before()
			val process = ProcessBuilder(process, *args).start()
			while (!isAdbServerOnline()) {
			}
			if (this !is Start) {
				process.inputStream.reader().readLines().filterNot { it.isNullOrEmpty() }.forEach {
					block?.invoke(it)
				}
			}
		} finally {
			after()
		}
	}

	fun isAdbServerOnline(): Boolean {
		try {
			Socket("localhost", 5037).close()
			return true
		} catch(e: IOException) {
			return false
		}
	}

	open fun before() {

	}

	open fun after() {

	}

}