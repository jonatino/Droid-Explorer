package com.droid.explorer

import com.droid.explorer.tracking.PathTracking.currentPath
import java.text.SimpleDateFormat

/**
 * Created by Jonathan on 4/23/2016.
 */
data class AndroidFile(var name: String, var date: String, var permissions: String) {

	fun isDirectory() = permissions.startsWith("d")
	fun isSymbolicLink() = permissions.startsWith("l")

	val absolutePath = (if (!isSymbolicLink()) currentPath else "") + name + "/"

	val type = if (isDirectory()) "directory" else if (isSymbolicLink()) "symlink" else "file"

	companion object {
		fun parse(input: String): AndroidFile {
			val fileData: MutableList<String> = input.split(" ").toMutableList()
			val permissions = fileData.removeAt(0)

			val iter = fileData.iterator()
			while (iter.hasNext() && !iter.next().matches(Regex("([0-9]{4})-([0-9]{2})-([0-9]{2})"))) {
				iter.remove()
			}

			var date = fileData.removeAt(0) + " " + fileData.removeAt(0)
			val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
			val outputFormat = SimpleDateFormat("M/d/yyyy h:mm a")
			date = outputFormat.format(inputFormat.parse(date))

			var name= ""
			fileData.forEach { name+=it+" " }
			name = name.trim()

			return AndroidFile(name, date, permissions)
		}
	}

}