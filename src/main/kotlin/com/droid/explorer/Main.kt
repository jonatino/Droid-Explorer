package com.droid.explorer

/**
 * Created by Jonathan on 4/20/2016.
 */
fun main(args: Array<String>) {
	executeAdb("devices")
	executeShell("pwd")
	executeShell("ls", "/data/data")
}

fun executeShell(vararg command: String) = executeAdb("shell", "su", "root", *command)

fun executeAdb(vararg command: String) {
	var process = ProcessBuilder("E:\\Dropbox\\Droid Explorer\\src\\main\\resources\\adb.exe", *command).start()
	read(process)
}

fun read(process: Process) = process.inputStream.reader().forEachLine { println(it) }