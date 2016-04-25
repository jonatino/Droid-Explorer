package com.droid.explorer.view

import com.droid.explorer.command.shell.impl.Pull
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.SeparatorMenuItem
import javafx.scene.control.TableCell
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import java.util.*

/**
 * Created by Jonathan on 4/24/2016.
 */
open class FileCell<AndroidFile, String> : TableCell<AndroidFile, String>() {

	init {
		/*	itemProperty().addListener({ observable, oldValue, newValue ->
				//if (newValue != null) {
				val tableRow = tableRow
				if (tableRow.item != null ) {
					val rowItem: com.droid.explorer.AndroidFile = tableRow?.item as com.droid.explorer.AndroidFile
					println(rowItem)


					setText("${newValue.toString()} , ${oldValue.toString()}")
				} else {
					println("$oldValue, $newValue")
				}

				//}
			})*/
	}

	override fun updateItem(item: String, empty: Boolean) {
		super.updateItem(item, empty);
		if (item == null || empty) {
			setText(null)
			setGraphic(null)
		} else {
			val rowMenu = ContextMenu()
			val contextItems = ArrayList<MenuItem>()
			if (tableRow?.item != null) {
				val file: com.droid.explorer.AndroidFile = tableRow?.item as com.droid.explorer.AndroidFile
				if (file.isDirectory()) {
					val open = MenuItem("Open", ImageView(Image(javaClass.getResource("../img/open.png").toExternalForm())))
					contextItems.add(open)
					//open.setOnAction({ event -> navigate(file.absolutePath) })

					contextItems.add(MenuItem("Compress", ImageView(Image(javaClass.getResource("../img/compress.png").toExternalForm()))))
					contextItems.add(SeparatorMenuItem())
				} else {
					//TODO download multiple files at once?
					val download = MenuItem("Download", ImageView(Image(javaClass.getResource("../img/download.png").toExternalForm())))
					download.setOnAction({ event -> Pull(file.absolutePath, "C:/Users/Jonathan/Desktop").callback { println(it) } })
					contextItems.add(download)
				}
				contextItems.add(SeparatorMenuItem())
				contextItems.add(MenuItem("Delete", ImageView(Image(javaClass.getResource("../img/delete.png").toExternalForm()))))
				val replace = MenuItem("Upload", ImageView(Image(javaClass.getResource("../img/download.png").toExternalForm())))
				/*		replace.setOnAction({ event ->
				val files = javafx.stage.FileChooser().showOpenMultipleDialog(primaryStage);
				if (files != null && files.isNotEmpty()) {
					files.forEach { Push(it.absolutePath, PathTracking.currentPath).callback { println(it) } }
				}
			})*/
				text = file.name
				if (file.isDirectory()) {
					graphic = ImageView(Image(javaClass.getResource("../img/folder.png").toExternalForm()))
				} else if (file.isSymbolicLink()) {
					graphic = ImageView(Image(javaClass.getResource("../img/link.png").toExternalForm()))
				} else {
					graphic = ImageView(Image(javaClass.getResource("../img/file.png").toExternalForm()))
				}
				contextItems.add(replace)
				rowMenu.items.addAll(contextItems)
				contextMenu = rowMenu
			}
		}
	}
}