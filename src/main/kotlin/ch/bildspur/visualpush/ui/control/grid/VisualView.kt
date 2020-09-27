package ch.bildspur.visualpush.ui.control.grid

import ch.bildspur.event.Event
import ch.bildspur.visualpush.model.visual.VisualGrid
import ch.bildspur.visualpush.visual.Visual
import javafx.scene.control.Label

class VisualView(val grid : VisualGrid, val visual : Visual, val x : Int, val y : Int) : BaseView() {
    val onVisualSelected = Event<Visual>()

    val label = Label("Empty")

    init {
        visual.name.onChanged += {
            label.text = it
        }
        visual.name.fireLatest()

        setOnMouseClicked {
            println("play clip")
            grid.play(x, y)
        }

        children.add(label)
    }
}