package com.droid.explorer.command.shell.impl

import com.droid.explorer.command.shell.ShellCommand
import com.droid.explorer.controller.Entry
import com.droid.explorer.tracking.PathTracking

/**
 * Created by Jonathan on 4/23/2016.
 */
class ListFiles(val directory: String, vararg extraArgs: String) : ShellCommand<Entry>() {

	override fun callback(action: (Entry) -> Any) = output().forEach { action(PathTracking.parseEntry(it)) }

	override val shellArgs = arrayOf("ls", *extraArgs, directory)

}