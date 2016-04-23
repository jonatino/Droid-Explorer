package com.droid.explorer

import com.droid.explorer.command.shell.impl.ListFiles
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.AnchorPane
import javafx.util.Callback
import org.controlsfx.control.BreadCrumbBar
import tornadofx.App
import tornadofx.View
import java.util.*

/**
 * Created by Jonathan on 4/23/2016.
 */

fun main(args: Array<String>) {
	Application.launch(AppClass::class.java)
}

class AppClass : App() {

	override val primaryView = DroidExplorer::class

}

class DroidExplorer : View() {

	override val root: AnchorPane by fxml()

	@FXML lateinit var fileTable: TableView<AndroidFile>
	@FXML lateinit var name: TableColumn<AndroidFile, String>
	@FXML lateinit var permissions: TableColumn<AndroidFile, String>
	@FXML lateinit var date: TableColumn<AndroidFile, String>
	@FXML lateinit var filePath: BreadCrumbBar<String>

	init {
		refresh()
		title = "Droid Explorer"

		name.cellValueFactory = PropertyValueFactory("name")
		date.cellValueFactory = PropertyValueFactory("date")
		permissions.cellValueFactory = PropertyValueFactory("permissions")

		fileTable.isEditable = true
		fileTable.requestLayout()

		fileTable.rowFactory = Callback<TableView<AndroidFile>, TableRow<AndroidFile>> {
			object : TableRow<AndroidFile>() {
				override fun updateItem(file: AndroidFile?, paramBoolean: Boolean) {
					if (file != null) {
						val rowMenu = ContextMenu()
						val contextItems = ArrayList<MenuItem>()

						if (file.isDirectory()) {
							val open = MenuItem("Open")
							contextItems.add(open)
							open.setOnAction({ event ->
								currentPath += file.name + "/"
								refresh()
							})
							contextItems.add(MenuItem("Compress"))
						} else {
							contextItems.add(MenuItem("Download"))
						}
						contextItems.add(MenuItem("Delete"))
						rowMenu.items.addAll(contextItems)
						contextMenu = rowMenu
						super.updateItem(file, paramBoolean)
					}
				}
			}
		}

		refresh()

		filePath.setOnCrumbAction {
			currentPath = currentPath.replaceAfter(it.selectedCrumb.value, "")
			refresh()
		}
	}

	companion object {
		@JvmStatic var currentPath = "/"
	}

	fun refresh() {
		var path = arrayOf("/", *currentPath.split("/").filterNot { it.isNullOrEmpty() }.toTypedArray())
		filePath.selectedCrumb = BreadCrumbBar.buildTreeModel(*path)

		val list = ArrayList<AndroidFile>()
		ListFiles(currentPath, "-l")({
			list.add(it)
		})

		fileTable.items = FXCollections.observableArrayList(list)
	}
}

