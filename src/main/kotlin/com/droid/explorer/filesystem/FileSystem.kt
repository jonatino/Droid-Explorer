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
		val path = arrayOf(root, *currentPath.parents.toTypedArray())
		droidExplorer.filePath.selectedCrumb = BreadCrumbBar.buildTreeModel(*path)

		val files = mutableListOf<Entry>()
		ListFiles(currentPath.absolutePath, "-l").run() { files.add(parseEntry(it)) }
		files.sortBy { it.type }

		droidExplorer.fileTable.items = FXCollections.observableArrayList(files)

		droidExplorer.back.isDisable = currentPath.parent == null
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

	fun parseEntry(input: String): Entry {
		val cachedEntry = cache[input]
		if (cachedEntry != null && cachedEntry.parent == currentPath) {
			return cachedEntry
		}
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
	}

}