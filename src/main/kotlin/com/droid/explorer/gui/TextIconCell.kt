package com.droid.explorer.gui

import com.droid.explorer.DroidExplorer
import com.droid.explorer.command.shell.impl.Pull
import com.droid.explorer.command.shell.impl.Push
import com.droid.explorer.controller.DirectoryEntry
import com.droid.explorer.controller.Entry
import com.droid.explorer.tracking.PathTracking
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.SeparatorMenuItem
import javafx.scene.control.TableCell
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.FileChooser

/**
 * Created by Jonathan on 4/24/2016.
 */
open class TextIconCell<T, S>(val droidExplorer: DroidExplorer) : TableCell<T, S>() {

	override fun updateItem(item: S, empty: Boolean) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			text = null
			graphic = null
		} else {
			val rowMenu = ContextMenu()
			if (tableRow?.item != null) {
				val file: Entry = tableRow?.item as Entry
				when (file) {
					is DirectoryEntry -> {
						val open = MenuItem("Open", ImageView(Image(javaClass.getResource("../img/open.png").toExternalForm())))
						rowMenu.items.add(open)
						open.setOnAction({ event -> droidExplorer.navigate(file.absolutePath) })

						rowMenu.items.add(MenuItem("Compress", ImageView(Image(javaClass.getResource("../img/compress.png").toExternalForm()))))
						rowMenu.items.add(SeparatorMenuItem())
					}
					else -> {
						//TODO download multiple files at once?
						val download = MenuItem("Download", ImageView(Image(javaClass.getResource("../img/download.png").toExternalForm())))
						download.setOnAction({ event -> Pull(file.absolutePath, "C:/Users/Jonathan/Desktop").callback { println(it) } })
						rowMenu.items.add(download)
					}
				}
				rowMenu.items.add(SeparatorMenuItem())
				rowMenu.items.add(MenuItem("Delete", ImageView(Image(javaClass.getResource("../img/delete.png").toExternalForm()))))

				val replace = MenuItem("Upload", ImageView(Image(javaClass.getResource("../img/download.png").toExternalForm())))
				replace.setOnAction({ event ->
					val files = FileChooser().showOpenMultipleDialog(droidExplorer.primaryStage);
					if (files != null && files.isNotEmpty()) {
						files.forEach { Push(it.absolutePath, PathTracking.currentPath).callback { println(it) } }
					}
				})

				text = file.name
				graphic = file.icon
				contextMenu = rowMenu
			}
		}
	}

}