package com.droid.explorer

/**
 * Created by Jonathan on 4/23/2016.
 */
data class AndroidFile(var name: String, var path: String, var permission: String) {

	fun isDirectory() = permission.startsWith("d")
	fun isFile() = !isDirectory()

}