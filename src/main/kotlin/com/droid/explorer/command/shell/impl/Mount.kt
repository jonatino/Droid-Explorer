package com.droid.explorer.command.shell.impl

import com.droid.explorer.command.shell.ShellCommand

/**
 * Created by Jonathan on 4/23/2016.
 */
class Mount(system: String = "/") : ShellCommand() {

    override val shellArgs = arrayOf("mount", "-o", "remount,rw", "\"$system\"")

}