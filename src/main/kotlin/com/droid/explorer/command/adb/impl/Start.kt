package com.droid.explorer.command.shell.impl

import com.droid.explorer.command.adb.AdbCommand

/**
 * Created by Jonathan on 4/23/2016.
 */
class Start() : AdbCommand() {

	override val args = arrayOf("start-server")

}