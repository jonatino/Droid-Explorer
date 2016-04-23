package com.droid.explorer

import javafx.application.Application
import javafx.scene.layout.AnchorPane
import tornadofx.App
import tornadofx.View

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

}

