package com.droid.explorer

import com.droid.explorer.command.shell.impl.ListFiles
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.util.Callback
import org.controlsfx.control.BreadCrumbBar
import tornadofx.App
import tornadofx.FX
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
	@FXML lateinit var back: Button
	@FXML lateinit var forward: Button
	@FXML lateinit var refresh: Button
	@FXML lateinit var home: Button

	init {
		title = "Droid Explorer"

		root.setStyle("-fx-background-color: #3d3d3d;");

		name.cellValueFactory = PropertyValueFactory("name")
		date.cellValueFactory = PropertyValueFactory("date")
		permissions.cellValueFactory = PropertyValueFactory("permissions")

		FX.stylesheets.add(javaClass.getResource("css/DroidExplorer.css").toExternalForm())

		back.graphic = ImageView(Image(javaClass.getResource("img/back.png").toExternalForm()))
		forward.graphic = ImageView(Image(javaClass.getResource("img/forward.png").toExternalForm()))
		refresh.graphic = ImageView(Image(javaClass.getResource("img/refresh.png").toExternalForm()))
		home.graphic = ImageView(Image(javaClass.getResource("img/home.png").toExternalForm()))

		fileTable.onMouseClicked = EventHandler<MouseEvent> { mouseEvent ->
			if (mouseEvent.clickCount === 2) {
				val file = fileTable.selectionModel.selectedItem
				if (file.isDirectory()) {
					navigate(file.absolutePath + "/")
				}
			}
		}

		fileTable.rowFactory = Callback<TableView<AndroidFile>, TableRow<AndroidFile>> {
			object : TableRow<AndroidFile>() {
				override fun updateItem(file: AndroidFile?, paramBoolean: Boolean) {
					if (file != null) {
						val rowMenu = ContextMenu()
						val contextItems = ArrayList<MenuItem>()

						if (file.isDirectory()) {
							val open = MenuItem("Open", ImageView(Image(javaClass.getResource("img/open.png").toExternalForm())))
							contextItems.add(open)
							open.setOnAction({ event -> navigate(file.absolutePath) })

							contextItems.add(MenuItem("Compress", ImageView(Image(javaClass.getResource("img/compress.png").toExternalForm()))))
						} else {
							contextItems.add(MenuItem("Download", ImageView(Image(javaClass.getResource("img/download.png").toExternalForm()))))
						}
						contextItems.add(MenuItem("Delete", ImageView(Image(javaClass.getResource("img/delete.png").toExternalForm()))))
						rowMenu.items.addAll(contextItems)
						contextMenu = rowMenu
					}
					super.updateItem(file, paramBoolean)
				}
			}
		}

		filePath.setOnCrumbAction { navigate(currentPath.replaceAfter(it.selectedCrumb.value, "") + "/") }

		navigate("/")
	}

	companion object {
		@JvmStatic var currentPath = "/"
	}

	fun navigate(path: String) {
		currentPath = path
		refresh()
	}

	fun refresh() {
		var path = arrayOf("/", *currentPath.split("/").filterNot { it.isNullOrEmpty() }.toTypedArray())
		filePath.selectedCrumb = BreadCrumbBar.buildTreeModel(*path)

		println("Hello $currentPath")
		val list = ArrayList<AndroidFile>()

		ListFiles(currentPath, "-l")({ list.add(it) })

		println(list)
		fileTable.items = FXCollections.observableArrayList(list)
	}
}

