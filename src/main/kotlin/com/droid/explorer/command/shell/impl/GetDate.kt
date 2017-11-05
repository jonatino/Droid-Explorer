package com.droid.explorer.command.shell.impl

import com.droid.explorer.command.shell.ShellCommand

/**
 * Created by Jonathan on 11/2/2017.
 */
class GetDate : ShellCommand() {

    override val shellArgs = arrayOf("date")

}