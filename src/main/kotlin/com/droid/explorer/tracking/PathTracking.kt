package com.droid.explorer.tracking

import com.droid.explorer.DroidExplorer
import com.droid.explorer.controller.DirectoryEntry
import com.droid.explorer.controller.Entry
import com.droid.explorer.controller.SymbolicLinkEntry
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jonathan on 4/23/2016.
 */
object PathTracking {

	var currentPath = "/"


	var currentIndex = -1
	val history = LinkedList<String>()

	val root = Node(null, null, "/")
	var node = root

	fun check(droidExplorer: DroidExplorer) {
		if (history.isNotEmpty() && currentIndex + 1 < history.size) {
			droidExplorer.forward.isDisable = false
		} else {
			droidExplorer.forward.isDisable = true
		}
		if (currentIndex > 0 && history.isNotEmpty()) {
			droidExplorer.back.isDisable = false
		} else {
			droidExplorer.back.isDisable = true
		}
	}

	fun forward(droidExplorer: DroidExplorer) {

	}

	fun back(droidExplorer: DroidExplorer) {
		currentIndex--
		println("Going back to: ${history[currentIndex]}")
		droidExplorer.navigate(history[currentIndex], false)
	}

	fun add(path: String) {
		//println("History $history")
		//println("History $currentIndex")
		//println("Wanting to add $path")
		//if (history.isNotEmpty())
		//println("Last Item ${history.get(currentIndex)} ${currentPath}")
		if (currentIndex > 0 && history.get(currentIndex) == path) {
			println("Dont add it")
		} else {
			history.add(path)
		}
		currentIndex = history.size - 1
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

		println(name)
		if (permissions.startsWith("l")) {
			return SymbolicLinkEntry("", name, date, permissions)
		} else if (permissions.startsWith("d")) {
			return DirectoryEntry("", name, date, permissions)
		} else if (permissions.startsWith("-")) {
			return DirectoryEntry("",  name, date, permissions)
		}
		throw RuntimeException("Unknown file type: $permissions, $date, $name")
	}

}