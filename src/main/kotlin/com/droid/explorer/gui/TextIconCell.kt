package com.droid.explorer.gui

import com.droid.explorer.Icons
import com.droid.explorer.command.adb.impl.Push
import com.droid.explorer.command.shell.impl.Mount
import com.droid.explorer.command.shell.impl.Pull
import com.droid.explorer.command.shell.impl.UnMount
import com.droid.explorer.controller.DirectoryEntry
import com.droid.explorer.controller.Entry
import com.droid.explorer.droidExplorer
import com.droid.explorer.tracking.PathTracking
import javafx.scene.Node
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.SeparatorMenuItem
import javafx.scene.control.TableCell
import javafx.scene.image.ImageView
import javafx.stage.FileChooser
import java.util.*

/**
 * Created by Jonathan on 4/24/2016.
 */
open class TextIconCell<T, S>() : TableCell<T, S>() {

	companion object {
		private val cache = HashMap<Int, FileCell>()
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
							rowMenu.items.addAll(open, MenuItem("Compress", ImageView(Icons.COMPRESS.image)), SeparatorMenuItem())
						}
						else -> {
							//TODO download multiple files at once?
							val download = MenuItem("Download", ImageView(Icons.DOWNLOAD.image))
							download.setOnAction({ event -> Pull(file.absolutePath(), "C:/Users/Jonathan/Desktop").run().forEach { println(it) } })
							rowMenu.items.add(download)
						}
					}

					val replace = MenuItem("Upload", ImageView(Icons.UPLOAD.image))
					replace.setOnAction({ event ->
						val files = FileChooser().showOpenMultipleDialog(droidExplorer.primaryStage);
						if (files != null && files.isNotEmpty()) {
							Mount().run()
							files.forEach { Push(it.absolutePath, PathTracking.currentPath.absolutePath()).run().forEach { println(it) } }
							UnMount().run()
						}
					})

					rowMenu.items.addAll(SeparatorMenuItem(), MenuItem("Delete", ImageView(Icons.DELETE.image)), replace)

					text = file.name
					graphic = ImageView(file.type().icon.image)
					contextMenu = rowMenu
					cache.put(file.hashCode(), FileCell(text, graphic, contextMenu))
				} else {
					text = labeled.text
					graphic = labeled.graphic
					contextMenu = labeled.contextMenu
				}
			}
		}
	}
}

class FileCell(val text: String, val graphic: Node, val contextMenu: ContextMenu)

