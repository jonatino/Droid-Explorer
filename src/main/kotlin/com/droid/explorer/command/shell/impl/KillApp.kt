package com.droid.explorer.command.shell.impl

import com.droid.explorer.command.shell.ShellCommand

/**
 * Created by Jonathan on 11/2/2017.
 */
class KillApp(packagename: String) : ShellCommand() {

    override val shellArgs = arrayOf("am", "force-stop", packagename)

}