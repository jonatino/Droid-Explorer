package com.droid.explorer

import com.droid.explorer.command.shell.impl.ListFiles
import com.droid.explorer.command.shell.impl.Pull
import com.droid.explorer.tracking.PathTracking
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

		primaryStage.minHeight = 300.0
		primaryStage.minWidth = 575.0

		root.style = "-fx-background-color: #3d3d3d;";

		name.cellValueFactory = PropertyValueFactory("name")
		date.cellValueFactory = PropertyValueFactory("date")
		permissions.cellValueFactory = PropertyValueFactory("permissions")

		fileTable.placeholder = Label("This folder is empty.");

		FX.stylesheets.add(javaClass.getResource("css/DroidExplorer.css").toExternalForm())

		back.graphic = ImageView(Image(javaClass.getResource("img/back.png").toExternalForm()))
		forward.graphic = ImageView(Image(javaClass.getResource("img/forward.png").toExternalForm()))
		refresh.graphic = ImageView(Image(javaClass.getResource("img/refresh.png").toExternalForm()))
		home.graphic = ImageView(Image(javaClass.getResource("img/home.png").toExternalForm()))

		refresh.setOnAction({ event -> refresh() })
		home.setOnAction({ event -> navigate("/") })

		//back.setOnAction({ event -> PathTracking.back(this) })
		//forward.setOnAction({ event -> PathTracking.forward(this) })

		fileTable.onMouseClicked = EventHandler<MouseEvent> { mouseEvent ->
			if (mouseEvent.clickCount === 2) {
				val file = fileTable.selectionModel.selectedItem
				if (file != null && file.isDirectory()) {
					navigate(file.absolutePath + "/")
				}
			}
		}

		fileTable.rowFactory = Callback<TableView<AndroidFile>, TableRow<AndroidFile>> {
			object : TableRow<AndroidFile>() {
				override fun updateItem(file: AndroidFile?, paramBoolean: Boolean) {
					val rowMenu = ContextMenu()
					val contextItems = ArrayList<MenuItem>()
					if (file != null) {
						if (file.isDirectory()) {
							val open = MenuItem("Open", ImageView(Image(javaClass.getResource("img/open.png").toExternalForm())))
							contextItems.add(open)
							open.setOnAction({ event -> navigate(file.absolutePath) })

							contextItems.add(MenuItem("Compress", ImageView(Image(javaClass.getResource("img/compress.png").toExternalForm()))))
							contextItems.add(SeparatorMenuItem())
						} else {
							//TODO download multiple files at once?
							val download = MenuItem("Download", ImageView(Image(javaClass.getResource("img/download.png").toExternalForm())))
							download.setOnAction({ event -> Pull(file.absolutePath, "C:/Users/Jonathan/Desktop")({ println(it) }) })
							contextItems.add(download)
						}
						contextItems.add(SeparatorMenuItem())
						contextItems.add(MenuItem("Delete", ImageView(Image(javaClass.getResource("img/delete.png").toExternalForm()))))
					}
					val replace = MenuItem("Upload", ImageView(Image(javaClass.getResource("img/download.png").toExternalForm())))
					replace.setOnAction({ event ->
						val files = javafx.stage.FileChooser().showOpenMultipleDialog(primaryStage);
						if (files != null && files.isNotEmpty()) {
							files.forEach { (com.droid.explorer.command.shell.impl.Push(it.absolutePath, currentPath))({ println(it) }) }
						}
					})
					contextItems.add(replace)
					rowMenu.items.addAll(contextItems)
					contextMenu = rowMenu
					super.updateItem(file, paramBoolean)
				}
			}
		}

		filePath.setOnCrumbAction { navigate(currentPath.replaceAfter(it.selectedCrumb.value, "") + "/") }

		navigate("/")
	}

	companion object {
		var currentPath = "/"
	}

	fun navigate(path: String) {
		//PathTracking.add(currentPath, path)
		currentPath = path
		refresh()
	}

	fun refresh() {
		PathTracking.check(this)

		var path = arrayOf("/", *currentPath.split("/").filterNot { it.isNullOrEmpty() }.toTypedArray())
		filePath.selectedCrumb = BreadCrumbBar.buildTreeModel(*path)

		val list = ArrayList<AndroidFile>()

		ListFiles(currentPath, "-l")({ list.add(it) })

		fileTable.items = FXCollections.observableArrayList(list)
	}
}

