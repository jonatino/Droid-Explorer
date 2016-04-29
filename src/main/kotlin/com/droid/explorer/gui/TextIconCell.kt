package com.droid.explorer.gui

import com.droid.explorer.gui.Icons
import com.droid.explorer.command.adb.impl.Push
import com.droid.explorer.command.shell.impl.Mount
import com.droid.explorer.command.shell.impl.Pull
import com.droid.explorer.command.shell.impl.UnMount
import com.droid.explorer.filesystem.entry.DirectoryEntry
import com.droid.explorer.filesystem.entry.Entry
import com.droid.explorer.droidExplorer
import com.droid.explorer.filesystem.FileSystem
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.stage.FileChooser
import java.util.*

/**
 * Created by Jonathan on 4/24/2016.
 */
open class TextIconCell<T, S>() : TableCell<T, S>() {

	companion object {
		private val cache = HashMap<Int, CachedFileCell>()
	}

	override fun updateItem(item: S, empty: Boolean) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			text = null
			graphic = null
		} else {
			val file: Entry? = tableRow.item as Entry?
			if (file != null) {
				val labeled = cache[file.hashCode()]
				if (labeled == null) {
					val rowMenu = ContextMenu()
					when (file) {
						is DirectoryEntry -> {
							val open = MenuItem("Open", ImageView(Icons.OPEN.image))
							open.setOnAction({ event -> file.navigate() })
							rowMenu.items.addAll(open, /*MenuItem("Compress", ImageView(Icons.COMPRESS.image)),*/ SeparatorMenuItem())
						}
					}

					//TODO download multiple files at once?
					val download = MenuItem("Download", ImageView(Icons.DOWNLOAD.image))
					download.setOnAction({ Pull(file.absolutePath, "C:/Users/Jonathan/Desktop/${file.name}").run().forEach { println(it) } })
					rowMenu.items.add(download)

					val replace = MenuItem("Upload", ImageView(Icons.UPLOAD.image))
					replace.setOnAction({
						val files = FileChooser().showOpenMultipleDialog(droidExplorer.primaryStage);
						if (files != null && files.isNotEmpty()) {
							Mount().run()
							files.forEach { Push(it.absolutePath, FileSystem.currentPath.absolutePath).run().forEach { println(it) } }
							UnMount().run()
						}
					})

					val delete = MenuItem("Delete", ImageView(Icons.DELETE.image))

					delete.setOnAction({
						val alert = Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
						alert.title = "Confirm File Deletion";
						alert.contentText = "Are you sure you want to delete \"$file\"? This action can NOT be reversed.";
						alert.headerText = null

						val result = alert.showAndWait()
						if (result.isPresent && result.get() == ButtonType.OK) {

						}
						println(result)
					})


					rowMenu.items.addAll(SeparatorMenuItem(), delete, replace)

					text = file.name
					graphic = ImageView(file.type.icon.image)
					contextMenu = rowMenu
					cache.put(file.hashCode(), CachedFileCell(text, graphic, contextMenu))
				} else {
					text = labeled.text
					graphic = labeled.graphic
					contextMenu = labeled.contextMenu
				}
			}
		}
	}
}

data class CachedFileCell(val text: String, val graphic: Node, val contextMenu: ContextMenu)

