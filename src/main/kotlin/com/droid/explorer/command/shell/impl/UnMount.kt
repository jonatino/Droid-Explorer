package com.droid.explorer.command.shell.impl

import com.droid.explorer.command.shell.ShellCommand
import com.droid.explorer.tracking.PathTracking

/**
 * Created by Jonathan on 4/23/2016.
 */
class UnMount(val system: String = "/") : ShellCommand<String>() {

	override fun after() {
		PathTracking.refresh()
	}

	override val shellArgs = arrayOf("mount", "-o", "remount,ro", system)

}