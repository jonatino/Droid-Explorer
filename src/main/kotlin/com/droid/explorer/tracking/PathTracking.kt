package com.droid.explorer.tracking

import com.droid.explorer.DroidExplorer
import java.util.*

/**
 * Created by Jonathan on 4/23/2016.
 */
object PathTracking {

	var currentIndex = -1;
	val history = ArrayList<String>()

	val root = Node(null, null, "/")
	var node = root

	fun check(droidExplorer: DroidExplorer) {
		if (history.isNotEmpty() && currentIndex + 1 < history.size) {
			droidExplorer.forward.isDisable = false
		} else {
			droidExplorer.forward.isDisable = true
		}
		if (history.isNotEmpty() && currentIndex > 1) {
			droidExplorer.back.isDisable = false
		} else {
			droidExplorer.back.isDisable = true
		}
		println(history)
	}

	fun forward(droidExplorer: DroidExplorer, node: Node<String>) {
		if (node.right != null) {
			val right = node.right!!
			right.left = node
			droidExplorer.navigate(right.value)
		}
	}

	fun back(droidExplorer: DroidExplorer, node: Node<String>) {
		if (node.left != null) {
			val left = node.left!!
			left.right = node
			droidExplorer.navigate(left.value)
		}
	}

}