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

package com.droid.explorer.filesystem.entry

import com.droid.explorer.filesystem.FileSystem
import com.droid.explorer.gui.Icons
import javafx.scene.image.ImageView
import java.util.*

/**
 * Created by Jonathan on 4/25/2016.
 */
abstract class Entry(val parent: Entry?, val name: String, val date: String, val permissions: String) {
	
	abstract val type: Type
	
	fun isRoot() = name == "/"
	fun isDirectory() = type === Type.DIRECTORY
	fun isSymbolicLink() = type === Type.SYMLINK
	
	var files = listOf<Entry>()
	
	var lastChild: Entry? = null
	
	val absolutePath: String by lazy {
		var path: String = "/"
		parents.forEach { path += "$it/" }
		if (type === Type.FILE) {
			path = path.substring(0, path.length - 1)
		}
		path
	}
	
	val parents: List<Entry?> by lazy {
		val entries = ArrayList<Entry?>()
		if (!isRoot()) entries.add(this)
		
		var currentEntry: Entry? = this
		while (currentEntry?.parent != null) {
			val parent = currentEntry.parent
			if (!parent!!.isRoot()) {
				entries.add(parent)
			}
			currentEntry = parent
		}
		entries.reversed()
	}
	
	fun navigate() {
		if (isDirectory())
			FileSystem.currentPath = this
	}
	
	fun contains(entry: Entry) = files.any { it -> it.type == entry.type && it.name == entry.name }
	
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is Entry) return false
		if (parent != other.parent) return false
		if (name != other.name) return false
		if (date != other.date) return false
		if (permissions != other.permissions) return false
		if (absolutePath != other.absolutePath) return false
		return true
	}
	
	override fun hashCode(): Int {
		var result = parent?.hashCode() ?: 0
		result += 31 * result + name.hashCode()
		result += 31 * result + date.hashCode()
		result += 31 * result + permissions.hashCode()
		result += 31 * result + absolutePath.hashCode()
		return result
	}
	
	override fun toString() = if (isRoot()) name else name.replace("/", "")
	
	enum class Type(val icon: ImageView) {
		DIRECTORY(Icons.DIRECTORY), SYMLINK(Icons.SYMLINK), FILE(Icons.FILE)
	}
	
}