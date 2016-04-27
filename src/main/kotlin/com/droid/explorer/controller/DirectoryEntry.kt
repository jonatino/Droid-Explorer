package com.droid.explorer.controller

/**
 * Created by Jonathan on 4/25/2016.
 */
class DirectoryEntry(parent: Entry?, name: String, date: String, permissions: String) : Entry(parent, name, date, permissions) {

	override fun type() = Type.DIRECTORY

}