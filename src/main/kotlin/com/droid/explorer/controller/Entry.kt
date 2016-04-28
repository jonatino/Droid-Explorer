package com.droid.explorer.controller

import com.droid.explorer.Icons
import com.droid.explorer.tracking.PathTracking
import javafx.scene.image.ImageView
import java.util.*

/**
 * Created by Jonathan on 4/25/2016.
 */
abstract class Entry(val parent: Entry?, val name: String, val date: String, val permissions: String) {

	abstract fun type(): Type

	fun absolutePath(): String {
		var path: String = "/"
		parents.forEach { path += "$it/" }
		if (type() == Type.FILE) {
			path = path.substring(0, path.length - 1)
		}
		return path
	}

	var lastChild: Entry? = null

	fun isRoot() = name == "/"
	fun isDirectory() = type() == Type.DIRECTORY
	fun isSymbolicLink() = type() == Type.SYMLINK

	fun navigate() {
		PathTracking.currentPath = this
	}

	val parents: List<Entry?> by lazy {
		val entires = ArrayList<Entry?>()
		if (!isRoot())
			entires.add(this)

		var currentEntry: Entry? = this
		while (currentEntry?.parent != null) {
			val parent = currentEntry!!.parent;
			if (!parent!!.isRoot()) {
				entires.add(parent)
			}
			currentEntry = parent
		}
		entires.reversed()
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Entry) return false
		if (parent != other.parent) return false
		if (name != other.name) return false
		if (date != other.date) return false
		if (permissions != other.permissions) return false
		if (absolutePath() != other.absolutePath()) return false
		return true
	}

	override fun hashCode(): Int {
		var result = parent?.hashCode() ?: 0
		result += 31 * result + name.hashCode()
		result += 31 * result + date.hashCode()
		result += 31 * result + permissions.hashCode()
		return result
	}

	override fun toString() = if (isRoot()) name else name.replace("/", "")

	enum class Type(val icon: ImageView) {
		DIRECTORY(Icons.DIRECTORY), SYMLINK(Icons.SYMLINK), FILE(Icons.FILE)
	}

}