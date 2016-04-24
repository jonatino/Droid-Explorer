package com.droid.explorer.command.shell.impl

import com.droid.explorer.command.shell.ShellCommand

/**
 * Created by Jonathan on 4/23/2016.
 */
class UnMount(val system: String = "/") : ShellCommand<String>() {

	override val shellArgs = arrayOf("mount", "-o", "remount,ro", system)

}