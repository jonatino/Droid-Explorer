package com.droid.explorer.controller

import javafx.scene.image.ImageView

/**
 * Created by Jonathan on 4/25/2016.
 */
abstract class Entry(var parent: String, var name: String, var date: String, var permissions: String) {

	abstract fun type(): Type
	abstract val icon: ImageView

	fun isDirectory() = type() == Type.DIRECTORY
	fun isSymbolicLink() = type() == Type.SYMLINK

	val absolutePath = "$parent/$name"

	enum class Type {
		FILE, DIRECTORY, SYMLINK
	}

}