package ch.bildspur.visualpush.ui.control

import ch.bildspur.visualpush.visual.Visual
import javafx.scene.control.Label
import javafx.scene.layout.Pane

class VisualView(val visual : Visual) : Pane() {

    init {
        val label = Label(visual.name.value)
        children.add(label)
    }
}