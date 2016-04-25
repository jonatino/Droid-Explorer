package com.droid.explorer.command.shell.impl

import com.droid.explorer.AndroidFile
import com.droid.explorer.command.shell.ShellCommand

/**
 * Created by Jonathan on 4/23/2016.
 */
class ListFiles(val directory: String, vararg extraArgs: String) : ShellCommand<AndroidFile>() {

	override fun callback(action: (AndroidFile) -> Any) = output().forEach { action(AndroidFile.parse(it)) }

	override val shellArgs = arrayOf("ls", *extraArgs, directory)

}