package com.droid.explorer

import com.droid.explorer.command.shell.ShellCommand
import com.droid.explorer.command.shell.impl.Copy
import com.droid.explorer.command.shell.impl.Move
import com.droid.explorer.filesystem.entry.Entry
import java.util.*

/**
 * Created by Jonathan on 5/23/2016.
 */
object Clipboard {

    enum class Mode {
        CUT {
            override fun transferFile(entry: Entry, target: Entry) = Move(entry.absolutePath, target.absolutePath)
        },
        COPY {
            override fun transferFile(entry: Entry, target: Entry) = Copy(entry.absolutePath, target.absolutePath)
        };

        abstract fun transferFile(entry: Entry, target: Entry): ShellCommand

    }

    var content = HashMap<Entry, Mode>()

    fun reset() = content.clear()

    fun moveTo(target: Entry) = content.forEach { entry, mode -> mode.transferFile(entry, target) }

}
