package com.droid.explorer.filesystem.entry

/**
 * Created by Jonathan on 4/25/2016.
 */
class SymbolicLinkEntry(parent: Entry?, name: String, date: String, permissions: String, val targetPath: String) : Entry(parent, name, date, permissions) {

	override val type = Type.SYMLINK

}