package com.droid.explorer.command.adb.impl

import com.droid.explorer.command.adb.AdbCommand

/**
 * Created by Jonathan on 4/23/2016.
 */
class DeviceSerial() : AdbCommand() {

	override val args = arrayOf("get-serialno")

}