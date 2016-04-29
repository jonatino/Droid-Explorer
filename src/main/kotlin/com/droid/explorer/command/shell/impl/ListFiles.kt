package com.droid.explorer.command.shell.impl

import com.droid.explorer.command.shell.ShellCommand
import com.droid.explorer.filesystem.FileSystem
import com.droid.explorer.filesystem.entry.Entry
import java.util.*

/**
 * Created by Jonathan on 4/23/2016.
 */
class ListFiles(val directory: String, vararg extraArgs: String) : ShellCommand<Entry>() {

	override fun run() = ArrayList<Entry>().apply { output().forEach { add(FileSystem.parseEntry(it)) }}

	override val shellArgs = arrayOf("ls", *extraArgs, "\"$directory\"")

}