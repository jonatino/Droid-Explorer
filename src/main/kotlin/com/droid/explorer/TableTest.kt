package com.droid.explorer

import javafx.application.Application
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import tornadofx.App
import tornadofx.View
import tornadofx.add

/**
 * Created by Jonathan on 4/23/2016.
 */

fun main(args: Array<String>) {
	Application.launch(AppClass2::class.java)
}

class AppClass2 : App() {

	override val primaryView = TableTest::class

}

class TableTest : View() {

	override val root =  VBox()

	internal val data = FXCollections.observableArrayList(AndroidFile("Jacob", "Smith", "jacob.smith@example.com"))

	init {
		primaryStage.setTitle("Table View Sample")
		primaryStage.setWidth(450.0)
		primaryStage.setHeight(500.0)
		val label = Label("Address Book")
		label.setFont(Font("Arial", 20.0))
		for (i in 0..299)
		{
			data.add(AndroidFile("Jacob", "Smith", "jacob.smith@example.com"))
		}
		var table = TableView<AndroidFile>()
		val firstNameCol = TableColumn<AndroidFile, String>("name")
		firstNameCol.setCellValueFactory(PropertyValueFactory<AndroidFile, String>("date"))
		firstNameCol.setCellFactory({ column ->
			object : TableCell<AndroidFile, String>() {
			override  fun updateItem(item:String?, empty:Boolean) {
				super.updateItem(item, empty)
				if (item == null || empty)
				{
					setText(null)
				}
				else
				{
					// Format date.
					setText("test")
					setGraphic(ImageView(Image(javaClass.getResource("img/folder.png").toExternalForm())))
				}
			}
		} })
		val lastNameCol = TableColumn<AndroidFile, String>("date")
		lastNameCol.setMinWidth(100.0)
		lastNameCol.setCellValueFactory(PropertyValueFactory<AndroidFile, String>("date"))
		val emailCol = TableColumn<AndroidFile, String>("permissions")
		emailCol.setMinWidth(200.0)
		emailCol.setCellValueFactory(PropertyValueFactory<AndroidFile, String>("permissions"))
		table.setItems(data)
		table.getColumns().addAll(firstNameCol, lastNameCol, emailCol)
		val vbox = VBox()
		vbox.setSpacing(5.0)
		vbox.setPadding(Insets(10.0, 0.0, 0.0, 10.0))
		vbox.getChildren().addAll(label, table)
		root.add(vbox)
	}
}