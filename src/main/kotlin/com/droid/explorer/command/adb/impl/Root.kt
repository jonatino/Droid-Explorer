package com.droid.explorer.command.adb.impl

import com.droid.explorer.command.adb.AdbCommand

/**
 * Created by Jonathan on 11/4/2017.
 */
class Root : AdbCommand() {

    override val args = arrayOf("root")

    override val readOutput = false

}