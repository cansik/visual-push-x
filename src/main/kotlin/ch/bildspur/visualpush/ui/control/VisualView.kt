package ch.bildspur.visualpush.ui.control

import ch.bildspur.visualpush.model.visual.VisualGrid
import ch.bildspur.visualpush.visual.Visual
import javafx.scene.control.Label
import javafx.scene.layout.Pane

class VisualView(val grid : VisualGrid, val visual : Visual, val x : Int, val y : Int) : EmptyView() {


    init {
        visual.name.onChanged += {
            label.text = it
        }
        visual.name.fireLatest()

        setOnMouseClicked {
            println("play clip")
            grid.play(x, y)
        }
    }
}