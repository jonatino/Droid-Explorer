package com.droid.explorer.command.shell.impl

import com.droid.explorer.command.adb.AdbCommand

/**
 * Created by Jonathan on 4/23/2016.
 */
class Push(val localFile: String, val remoteFile: String = "") : AdbCommand<String>() {

	override fun callback(action: (String) -> Any) {
		Mount()
		super.callback(action)
		UnMount()
	}

	override val args = arrayOf("push", localFile, remoteFile)

}