package com.droid.explorer.controller

import javafx.scene.image.Image

/**
 * Created by Jonathan on 4/25/2016.
 */
class SymbolicLinkEntry(parent: String, name: String, date: String, permissions: String) : Entry(parent, name, date, permissions) {

	override val icon by lazy { javafx.scene.image.ImageView(Image(javaClass.getResource("../img/link.png").toExternalForm())) }

	override fun type() = Type.SYMLINK

}