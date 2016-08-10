/*
 *    Copyright 2016 Jonathan Beaudoin
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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

    private val imageView by lazy { ImageView(Image(DroidExplorer::class.java.getResource(file).toExternalForm())) }

    operator fun getValue(thisRef: Any?, property: KProperty<*>) = imageView

}