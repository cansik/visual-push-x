package ch.bildspur.visualpush.ui.control.grid

import ch.bildspur.event.Event
import javafx.scene.control.Label

class EmptyView : BaseView() {
    val onSelected = Event<EmptyView>()

    val label = Label("Empty")

    init {
        width = 80.0
        height = 30.0

        children.add(label)
    }
}