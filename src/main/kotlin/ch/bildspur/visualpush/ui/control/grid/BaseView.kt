package ch.bildspur.visualpush.ui.control.grid

import ch.bildspur.model.DataModel
import ch.bildspur.visualpush.ui.util.toRGBCode
import javafx.scene.layout.Pane
import javafx.scene.paint.Color

open class BaseView : Pane() {
    val borderColor = DataModel(Color.BLACK)

    init {
        prefWidth = 100.0
        prefHeight = 100.0

        borderColor.onChanged += {
            style = """
            -fx-padding: 10;
            -fx-border-style: solid outside;
            -fx-border-width: 2.0;
            -fx-border-radius: 5px;
            -fx-border-color: ${it.toRGBCode()};
            -fx-background-color: #969696;
            -fx-background-radius: 5px;
        """.trimIndent()
        }
        borderColor.fireLatest()
    }
}