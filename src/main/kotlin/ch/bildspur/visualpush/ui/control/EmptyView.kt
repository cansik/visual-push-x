package ch.bildspur.visualpush.ui.control

import javafx.scene.control.Label
import javafx.scene.layout.Pane

open class EmptyView : Pane() {
    val label = Label("Empty")

    init {
        width = 80.0
        height = 30.0

        children.add(label)
    }
}