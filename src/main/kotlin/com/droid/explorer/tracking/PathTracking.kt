package com.droid.explorer.tracking

import com.droid.explorer.DroidExplorer
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
		if (currentIndex > 0&& history.isNotEmpty()) {
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
		currentIndex = history.size-1
	}

}