package com.droid.explorer.controller

import javafx.scene.image.Image

/**
 * Created by Jonathan on 4/25/2016.
 */
class FileEntry(parent: String, name: String, date: String, permissions: String) : Entry(parent, name, date, permissions) {

	override val icon by lazy { javafx.scene.image.ImageView(Image(javaClass.getResource("../img/file.png").toExternalForm())) }

	override fun type() = Type.FILE

}