package com.droid.explorer

import java.text.SimpleDateFormat

/**
 * Created by Jonathan on 4/23/2016.
 */
data class AndroidFile(var name: String, var date: String, var permissions: String) {

	fun isDirectory() = permissions.startsWith("d")
	fun isSymbolicLink() = permissions.startsWith("l")

	val absolutePath = (if (!isSymbolicLink()) DroidExplorer.currentPath else "") + name

	companion object {
		 fun parse(input: String): AndroidFile {
			val fileData = input.split(" ").filterNot { input.isNullOrEmpty() }
			// println(fileData)
			var name = fileData.last()
			var date = fileData[fileData.lastIndex - 2] + " " + fileData[fileData.lastIndex - 1]
			var permissions = fileData.first()

			if (permissions.startsWith("l"))
				date = fileData[fileData.lastIndex - 4] + " " + fileData[fileData.lastIndex - 3]

			val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
			val outputFormat = SimpleDateFormat("M/d/yyyy h:mm a")
			date = outputFormat.format(inputFormat.parse(date))

			return AndroidFile(name, date, permissions)
		}
	}

}