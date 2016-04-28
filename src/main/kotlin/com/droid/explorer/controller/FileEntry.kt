package com.droid.explorer.controller

/**
 * Created by Jonathan on 4/25/2016.
 */
class FileEntry(parent: Entry?, name: String, date: String, permissions: String) : Entry(parent, name, date, permissions) {

	override val type = Type.FILE

}