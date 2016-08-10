package com.droid.explorer.command.adb

import com.droid.explorer.DroidExplorer
import com.droid.explorer.command.Command
import com.droid.explorer.command.shell.impl.Start
import java.io.IOException
import java.net.Socket

/**
 * Created by Jonathan on 4/23/2016.
 */
abstract class AdbCommand : Command {

    private val process by lazy { "\"${DroidExplorer::class.java.getResource("adb/adb.exe").toURI().path.substring(1)}\"" }

    override fun run(block: ((String) -> Unit)?) {
        try {
            before()
            val process = ProcessBuilder(process, *args).start()
            while (!isAdbServerOnline()) {
            }
            if (this !is Start) {
                process.inputStream.reader().readLines().filterNot { it.isNullOrEmpty() }.forEach {
                    block?.invoke(it)
                }
            }
        } finally {
            after()
        }
    }

    fun isAdbServerOnline(): Boolean {
        try {
            Socket("localhost", 5037).close()
            return true
        } catch(e: IOException) {
            return false
        }
    }

}