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
import javafx.collections.ObservableList
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
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
	
	val content = HashMap<Entry, Mode>()
	
	fun add(mode: Mode, entry: ObservableList<Entry>) {
		reset()
		entry.forEach { content.put(it, mode) }
	}
	
	fun reset() = content.clear()
	
	fun isEmpty() = content.isEmpty()
	
	fun moveTo(target: Entry, prompt: Boolean = true) {
		content.forEach { entry, mode ->
			when {
				prompt && target.contains(entry) -> {
					val alert = Alert(Alert.AlertType.CONFIRMATION)
					alert.title = "Replace or Skip Files"
					alert.headerText = "Copying 1 files from ${entry.parent} to ${target.parent}"
					alert.contentText = "The destination already has a file named \"${entry.name}\""
					
					val replace = ButtonType("Replace")
					val skip = ButtonType("Skip")
					
					alert.buttonTypes.setAll(replace, skip)
					
					val result = alert.showAndWait()
					if (result.get() === replace) mode.transferFile(entry, target).run()
				}
				else -> mode.transferFile(entry, target).run()
			}
		}
	}
	
}
