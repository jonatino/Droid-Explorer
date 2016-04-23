package com.droid.explorer.command.shell.impl

import com.droid.explorer.AndroidFile
import com.droid.explorer.command.shell.ShellCommand

/**
 * Created by Jonathan on 4/23/2016.
 */
class ListFiles(val directory: String, vararg extraArgs: String) : ShellCommand<AndroidFile>() {

	override operator fun invoke(action: (AndroidFile) -> Any) {
		output().forEach {
			val fileData = it.split(" ").filterNot { it.isNullOrEmpty() }
			action(AndroidFile(fileData[fileData.lastIndex], "", fileData[0]))
		}
	}

	override val shellArgs = arrayOf("ls", *extraArgs, directory)

}