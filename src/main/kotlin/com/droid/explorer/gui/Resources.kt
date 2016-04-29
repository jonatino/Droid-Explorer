package com.droid.explorer.gui

import com.droid.explorer.DroidExplorer
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import kotlin.reflect.KProperty

/**
 * Created by Jonathan on 4/25/2016.
 */
object Icons {

	val DIRECTORY by img("img/folder.png")
	val FILE by img("img/file.png")
	val SYMLINK by img("img/link.png")
	val BACK by img("img/back.png")
	val FORWARD by img("img/forward.png")
	val HOME by img("img/home.png")
	val COMPRESS by img("img/compress.png")
	val COPY by img("img/copy.png")
	val CUT by img("img/cut.png")
	val DELETE by img("img/delete.png")
	val DOWNLOAD by img("img/download.png")
	val FAVICON by img("img/favicon.png")
	val OPEN by img("img/open.png")
	val PASTE by img("img/paste.png")
	val REFRESH by img("img/refresh.png")
	val UPLOAD by img("img/upload.png")

}

object Css {

	val MAIN by lazy { DroidExplorer::class.java.getResource("css/DroidExplorer.css").toExternalForm() }

}

class img(val file: String) {
	private var imageView = ImageView(Image(DroidExplorer::class.java.getResource(file).toExternalForm()))
	operator fun getValue(thisRef: Any?, property: KProperty<*>) = imageView

}