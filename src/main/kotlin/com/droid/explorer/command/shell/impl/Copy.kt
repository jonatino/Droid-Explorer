package com.droid.explorer.command.shell.impl

import com.droid.explorer.command.shell.ShellCommand

/**
 * Created by Jonathan on 4/23/2016.
 */
class Copy(file: String, target: String) : ShellCommand() {

    override val shellArgs = arrayOf("cp", "-r", "\"$file\"", "\"$target\"")

}