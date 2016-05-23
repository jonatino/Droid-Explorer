package com.droid.explorer

import com.droid.explorer.command.adb.impl.DeviceSerial
import com.droid.explorer.command.adb.impl.DeviceState
import com.droid.explorer.command.shell.impl.Start
import com.droid.explorer.filesystem.FileSystem
import com.droid.explorer.filesystem.entry.Entry
import com.droid.explorer.gui.Css
import com.droid.explorer.gui.Icons
import com.droid.explorer.gui.TextIconCell
import javafx.animation.Animation
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.application.Application
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import javafx.util.Duration
import org.controlsfx.control.BreadCrumbBar
import tornadofx.App
import tornadofx.FX
import tornadofx.FX.Companion.stylesheets
import tornadofx.View
import tornadofx.find
import kotlin.concurrent.thread
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
	@FXML lateinit var status: Label

	init {
		Start().run()

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

		refresh.setOnAction({ FileSystem.refresh() })
		home.setOnAction({ FileSystem.root.navigate() })

		back.setOnAction({ FileSystem.back() })
		forward.setOnAction({ FileSystem.forward() })

		name.setCellFactory({ TextIconCell() })

		fileTable.onMouseClicked = EventHandler<MouseEvent> { mouseEvent ->
			println(mouseEvent)
			if (mouseEvent.clickCount % 2 === 0) {
				val file = fileTable.selectionModel.selectedItem
				file.navigate()
			}
		}

		fileTable.selectionModel.selectionMode = SelectionMode.MULTIPLE

		filePath.setOnCrumbAction { it.selectedCrumb.value!!.navigate() }

		thread {
			val timeline = Timeline(KeyFrame(Duration.ZERO, EventHandler {
				DeviceState().run() {
					if (it == "unknown") {
						status.text = "Disconnected"
						fileTable.items.clear()
					} else if (it == "device" && !status.text.startsWith("C")) {
						DeviceSerial().run() {
							status.text = "Connected: $it"
							FileSystem.refresh()
						}
					}
				}
			}), KeyFrame(Duration.millis(250.0)))
			timeline.cycleCount = Animation.INDEFINITE
			timeline.play()
		}

	}

}

