package com.droid.explorer.command.shell.impl

import com.droid.explorer.command.shell.ShellCommand

/**
 * Created by Jonathan on 4/23/2016.
 */
class RunApp(packagename: String, activity: String) : ShellCommand() {

    override val shellArgs = arrayOf("am", "start", "-n", "$packagename/$activity")

}