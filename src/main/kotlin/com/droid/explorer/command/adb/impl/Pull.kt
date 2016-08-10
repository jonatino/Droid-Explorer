package com.droid.explorer.command.adb.impl

import com.droid.explorer.command.adb.AdbCommand

/**
 * Created by Jonathan on 4/23/2016.
 */
class Pull(val remoteFile: String, val localFile: String = "") : AdbCommand() {

	override val args = arrayOf("pull", "\"$remoteFile\"", "\"$localFile\"")

}