package ch.bildspur.visualpush.ui.control

import ch.bildspur.visualpush.visual.Visual
import javafx.scene.control.Label
import javafx.scene.layout.Pane

class VisualView(val visual : Visual) : EmptyView() {


    init {
        visual.name.onChanged += {
            label.text = it
        }
        visual.name.fireLatest()

        setOnMouseClicked {
            println("play clip")
        }
    }
}