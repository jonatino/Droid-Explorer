package com.droid.explorer

import com.droid.explorer.controller.Entry
import com.droid.explorer.gui.TextIconCell
import com.droid.explorer.tracking.PathTracking
import javafx.application.Application
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import org.controlsfx.control.BreadCrumbBar
import tornadofx.App
import tornadofx.FX
import tornadofx.FX.Companion.stylesheets
import tornadofx.View
import tornadofx.find
import kotlin.properties.Delegates.notNull

/**
 * Created by Jonathan on 4/23/2016.
 */

fun main(args: Array<String>) {
	Application.launch(AppClass::class.java)
}

class AppClass : App() {

	override val primaryView = DroidExplorer::class

	override fun start(stage: Stage) {
		FX.primaryStage = stage
		FX.application = this

		try {
			val view = find(primaryView)
			droidExplorer = view

			stage.apply {
				scene = Scene(view.root)
				scene.stylesheets.addAll(FX.stylesheets)
				titleProperty().bind(view.titleProperty)
				show()
			}
		} catch (ex: Exception) {
			ex.printStackTrace()
		}
	}

}

var droidExplorer by notNull<DroidExplorer>()

class DroidExplorer : View() {

	override val root: AnchorPane by fxml()

	@FXML lateinit var fileTable: TableView<Entry>
	@FXML lateinit var name: TableColumn<Entry, String>
	@FXML lateinit var date: TableColumn<Entry, String>
	@FXML lateinit var permissions: TableColumn<Entry, String>
	@FXML lateinit var type: TableColumn<Entry, String>
	@FXML lateinit var filePath: BreadCrumbBar<Entry>
	@FXML lateinit var back: Button
	@FXML lateinit var forward: Button
	@FXML lateinit var refresh: Button
	@FXML lateinit var home: Button
	@FXML lateinit var path: Label

	init {
		title = "Droid Explorer"

		primaryStage.minHeight = 300.0
		primaryStage.minWidth = 575.0

		name.cellValueFactory = PropertyValueFactory("name")
		date.cellValueFactory = PropertyValueFactory("date")
		permissions.cellValueFactory = PropertyValueFactory("permissions")
		type.cellValueFactory = PropertyValueFactory("type")

		fileTable.placeholder = Label("This folder is empty.");

		stylesheets.add(Css.MAIN)
		primaryStage.icons.add(Icons.FAVICON.image);

		back.graphic = Icons.BACK
		forward.graphic = Icons.FORWARD
		refresh.graphic = Icons.REFRESH
		home.graphic = Icons.HOME

		refresh.setOnAction({ PathTracking.refresh() })
		home.setOnAction({ PathTracking.root.navigate() })

		back.setOnAction({ PathTracking.back() })
		forward.setOnAction({ PathTracking.forward() })

		name.setCellFactory({ TextIconCell() })

		fileTable.onMouseClicked = EventHandler<MouseEvent> { mouseEvent ->
			println(mouseEvent)
			if (mouseEvent.clickCount % 2 === 0) {
				val file = fileTable.selectionModel.selectedItem
				file.navigate()
			}
		}

		filePath.setOnCrumbAction { it.selectedCrumb.value!!.navigate() }

		PathTracking.refresh(this)
	}

}

