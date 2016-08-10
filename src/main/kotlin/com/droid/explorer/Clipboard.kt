/*
 *    Copyright 2016 Jonathan Beaudoin
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
