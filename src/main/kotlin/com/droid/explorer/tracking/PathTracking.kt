package com.droid.explorer.tracking

import com.droid.explorer.DroidExplorer
import com.droid.explorer.command.shell.impl.ListFiles
import com.droid.explorer.controller.DirectoryEntry
import com.droid.explorer.controller.Entry
import com.droid.explorer.controller.FileEntry
import com.droid.explorer.controller.SymbolicLinkEntry
import com.droid.explorer.droidExplorer
import javafx.collections.FXCollections
import org.controlsfx.control.BreadCrumbBar
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jonathan on 4/23/2016.
 */
object PathTracking {

	val root: Entry = DirectoryEntry(null, "/", "", "")
	var lastPath: Entry? = null
	var currentPath: Entry = root
		set(value) {
			lastPath = field
			field = value
			refresh()
		}


	var currentIndex = -1
	val history = LinkedList<String>()

	fun check() {
		/*if (history.isNotEmpty() && currentIndex + 1 < history.size) {
			droidExplorer.forward.isDisable = false
		} else {
			droidExplorer.forward.isDisable = true
		}
		if (currentIndex > 0 && history.isNotEmpty()) {
			droidExplorer.back.isDisable = false
		} else {
			droidExplorer.back.isDisable = true
		}*/
	}

	fun forward() {

	}

	fun back() {
	}

	fun add(path: String) {
		/*if (currentIndex > 0 && history.get(currentIndex) == path) {
			println("Dont add it")
		} else {
			history.add(path)
		}
		currentIndex = history.size - 1*/
	}

	fun refresh(de: DroidExplorer = droidExplorer) {
		val path = arrayOf(root, *currentPath.parents.toTypedArray())
		de.filePath.selectedCrumb = BreadCrumbBar.buildTreeModel(*path)

		val files = ListFiles(currentPath.absolutePath(), "-l").run()
		files.sortBy { it.type() }

		de.fileTable.items = FXCollections.observableArrayList(files)

		de.path.text = currentPath.absolutePath()
	}

	fun parseEntry(input: String): Entry {
		val fileData: MutableList<String> = input.split(" ").toMutableList()
		val permissions = fileData.removeAt(0)

		val iter = fileData.iterator()
		while (iter.hasNext() && !iter.next().matches(Regex("([0-9]{4})-([0-9]{2})-([0-9]{2})"))) {
			iter.remove()
		}

		var date = fileData.removeAt(0) + " " + fileData.removeAt(0)
		date = SimpleDateFormat("M/d/yyyy h:mm a").format(SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date))

		var name = ""
		fileData.forEach { name += it + " " }
		name = name.trim()

		if (permissions.startsWith("l")) {
			val split = name.split(" -> ")
			name = split.first()
			val targetPath = split.last()
			return SymbolicLinkEntry(currentPath, name, date, permissions, targetPath)
		} else if (permissions.startsWith("d")) {
			return DirectoryEntry(currentPath, name, date, permissions)
		} else if (permissions.startsWith("-")) {
			return FileEntry(currentPath, name, date, permissions)
		}
		throw RuntimeException("Unknown file type: $permissions, $date, $name")
	}

}