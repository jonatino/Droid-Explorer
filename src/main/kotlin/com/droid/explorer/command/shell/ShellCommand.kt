package com.droid.explorer.command.shell

import com.droid.explorer.command.adb.AdbCommand

/**
 * Created by Jonathan on 4/23/2016.
 */
abstract class ShellCommand : AdbCommand() {

	abstract val shellArgs: Array<String>

	override final val args by lazy { arrayOf("shell", "su", "root", *shellArgs) }

}