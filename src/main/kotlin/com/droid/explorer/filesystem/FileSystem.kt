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

package com.droid.explorer.filesystem

import com.droid.explorer.command.shell.impl.ListFiles
import com.droid.explorer.droidExplorer
import com.droid.explorer.filesystem.entry.DirectoryEntry
import com.droid.explorer.filesystem.entry.Entry
import com.droid.explorer.filesystem.entry.FileEntry
import com.droid.explorer.filesystem.entry.SymbolicLinkEntry
import javafx.collections.FXCollections
import org.controlsfx.control.BreadCrumbBar
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jonathan on 4/23/2016.
 */
object FileSystem {
	
	val root: Entry = DirectoryEntry(null, "/", "", "")
	
	var currentPath: Entry = root
		set(value) {
			field = value
			refresh()
		}
	
	fun refresh() {
		val path = if (!droidExplorer.connected) emptyArray() else arrayOf(root, *currentPath.parents.toTypedArray())
		droidExplorer.filePath.selectedCrumb = BreadCrumbBar.buildTreeModel(*path)
		
		val files = mutableListOf<Entry>()
		ListFiles(currentPath.absolutePath, "-l").run {
			val entry = parseEntry(it)
			if (entry != null)
				files.add(entry)
		}
		files.sortBy { it.type }
		
		currentPath.files = files
		droidExplorer.fileTable.items = FXCollections.observableArrayList(files)
		droidExplorer.fileTable.selectionModel.select(currentPath.lastChild)
		droidExplorer.fileTable.scrollTo(currentPath.lastChild)
		droidExplorer.home.isDisable = currentPath.parent == null
		droidExplorer.back.isDisable = currentPath.parent == null
		droidExplorer.refresh.isDisable = !droidExplorer.connected
		droidExplorer.forward.isDisable = currentPath.lastChild == null
	}
	
	fun forward() {
		currentPath = currentPath.lastChild!!
	}
	
	fun back() {
		currentPath.parent!!.lastChild = currentPath
		currentPath = currentPath.parent!!
	}
	
	private val cache = HashMap<String, Entry>()
	
	private val from = SimpleDateFormat("M/d/yyyy h:mm a")
	private val to = SimpleDateFormat("yyyy-MM-dd HH:mm")
	
	private val pattern = Regex("([0-9]{4})-([0-9]{2})-([0-9]{2})")
	
	fun parseEntry(input: String): Entry? {
		try {
			val cachedEntry = cache[input]
			if (cachedEntry != null && cachedEntry.parent == currentPath) {
				return cachedEntry
			}
			
			val fileData = input.split(" ").toMutableList()
			val permissions = fileData.removeAt(0)
			
			val iter = fileData.iterator()
			while (iter.hasNext() && !iter.next().matches(pattern)) {
				iter.remove()
			}
			
			var date = fileData.removeAt(0) + " " + fileData.removeAt(0)
			date = from.format(to.parse(date))
			
			var name = fileData.joinToString(" ") { it }.trim()
			
			val entry: Entry
			if (permissions.startsWith("l")) {
				val split = name.split(" -> ")
				name = split.first()
				val targetPath = split.last()
				entry = SymbolicLinkEntry(currentPath, name, date, permissions, targetPath)
			} else if (permissions.startsWith("d")) {
				entry = DirectoryEntry(currentPath, name, date, permissions)
			} else {
				entry = FileEntry(currentPath, name, date, permissions)
			}
			
			cache.put(input, entry)
			return entry
		} catch (e: Throwable) {
			return null
		}
	}
	
}