/*
 *    Copyright 2016 Jonathan Beaudoin
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.droid.explorer.gui

import com.droid.explorer.Clipboard
import com.droid.explorer.Clipboard.Mode.COPY
import com.droid.explorer.Clipboard.Mode.CUT
import com.droid.explorer.command.adb.impl.Pull
import com.droid.explorer.command.adb.impl.Push
import com.droid.explorer.command.shell.impl.Mount
import com.droid.explorer.command.shell.impl.Remove
import com.droid.explorer.command.shell.impl.UnMount
import com.droid.explorer.droidExplorer
import com.droid.explorer.filesystem.FileSystem
import com.droid.explorer.filesystem.entry.DirectoryEntry
import com.droid.explorer.filesystem.entry.Entry
import com.droid.explorer.util.selectedItems
import com.droid.explorer.util.withSelected
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.stage.FileChooser
import java.util.*

/**
 * Created by Jonathan on 4/24/2016.
 */
open class TextIconCell<S> : TableCell<Entry, S>() {

    companion object {
        private val cache = HashMap<Int, CachedFileCell>()
    }

    override fun updateItem(item: S, empty: Boolean) {
        super.updateItem(item, empty)
        if (item == null || empty) {
            text = null
            graphic = null
        } else (tableRow.item as? Entry)?.let {
            val file = it
            val labeled = cache[file.hashCode()]
            if (labeled == null) {
                val rowMenu = ContextMenu()
                when (file) {
                    is DirectoryEntry -> {
                        val open = MenuItem("Open", ImageView(Icons.OPEN.image))
                        open.setOnAction { event -> file.navigate() }
                        rowMenu.items.addAll(open, SeparatorMenuItem())
                    }
                }

                val download = MenuItem("Download", ImageView(Icons.DOWNLOAD.image))
                download.setOnAction {
                    tableView.withSelected {
                        Pull(it.absolutePath, System.getProperty("user.home") + "\\Desktop\\${it.name}").run()
                    }
                }

                val move = Menu("Move")
                val paste = MenuItem("Paste", ImageView(Icons.PASTE.image))

                val cut = MenuItem("Cut", ImageView(Icons.CUT.image))
                cut.setOnAction {
	                Clipboard.add(CUT, tableView.selectedItems())
                }
                val copy = MenuItem("Copy", ImageView(Icons.COPY.image))
                copy.setOnAction {
	                Clipboard.add(COPY, tableView.selectedItems())
                }
                paste.setOnAction {
	                Mount().run()
	                Clipboard.moveTo(FileSystem.currentPath)
	                UnMount().run()
                }

                move.items.addAll(cut, copy, paste)

                rowMenu.items.addAll(move, download)

                val upload = MenuItem("Upload", ImageView(Icons.UPLOAD.image))
                upload.setOnAction {
                    val files = FileChooser().showOpenMultipleDialog(droidExplorer.primaryStage)
                    if (files != null && files.isNotEmpty()) {
                        Mount().run()
	                    files.forEach { Push(it.absolutePath, FileSystem.currentPath.absolutePath).run(::println) }
                        UnMount().run()
                    }
                }

                val delete = MenuItem("Delete", ImageView(Icons.DELETE.image))

                delete.setOnAction {
                    val alert = Alert(Alert.AlertType.CONFIRMATION)
                    alert.title = "Confirm File Deletion"
                    alert.contentText = "Are you sure you want to delete \"$file\"? This action can NOT be reversed."
                    alert.headerText = null

                    val result = alert.showAndWait()
                    if (result.isPresent && result.get() == ButtonType.OK) {
                        Mount().run()
                        Remove(file.absolutePath).run()
                        UnMount().run()
                    }
                }


                rowMenu.items.addAll(SeparatorMenuItem(), delete, upload)

                text = file.name
                graphic = ImageView(file.type.icon.image)
                contextMenu = rowMenu
                cache.put(file.hashCode(), CachedFileCell(text, graphic, contextMenu, {
	                // paste.isDisable = Clipboard.isEmpty()
                }))
            } else {
                text = labeled.text
                graphic = labeled.graphic
                contextMenu = labeled.contextMenu
                labeled.action.invoke()
            }
        }
    }

}

data class CachedFileCell(val text: String, val graphic: Node, val contextMenu: ContextMenu, val action: () -> Unit)

