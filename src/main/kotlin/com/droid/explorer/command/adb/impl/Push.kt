package com.droid.explorer.command.adb.impl

import com.droid.explorer.command.adb.AdbCommand

/**
 * Created by Jonathan on 4/23/2016.
 */
class Push(val localFile: String, val remoteFile: String = "") : AdbCommand<String>() {

	override val args = arrayOf("push", "\"$localFile\"", "\"$remoteFile\"")

}