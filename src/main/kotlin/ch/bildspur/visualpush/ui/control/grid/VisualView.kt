package ch.bildspur.visualpush.ui.control.grid

import ch.bildspur.event.Event
import ch.bildspur.visualpush.model.visual.VisualGrid
import ch.bildspur.visualpush.util.toImage
import ch.bildspur.visualpush.visual.Visual
import ch.bildspur.visualpush.visual.types.PlayMode
import javafx.application.Platform
import javafx.scene.Cursor
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.paint.Color

class VisualView(val grid: VisualGrid, val visual: Visual, val x: Int, val y: Int) : BaseView() {
    val onVisualSelected = Event<Visual>()

    val label = Label("Empty")

    val imageView = ImageView()
    val overlay = ImageView()

    init {
        // todo: think about how to deconstruct the handlers on grid reset
        visual.name.onChanged += {
            label.text = it
        }
        visual.name.fireLatest()

        visual.playMode.onChanged += {
            borderColor.value = when (it) {
                PlayMode.OneShot -> Color.RED
                PlayMode.Hold -> Color.BLUE
                PlayMode.Loop -> Color.GREEN
                PlayMode.ContinuousHold -> Color.AQUAMARINE
            }
        }
        visual.playMode.fireLatest()

        setOnMousePressed {
            grid.actionStarted(x, y)
        }

        setOnMouseReleased {
            grid.actionEnded(x, y)
        }


        imageView.prefWidth(100.0)
        imageView.prefHeight(100.0)
        visual.preview.onChanged += {
            Platform.runLater {
                imageView.image = visual.preview.value.toImage()
            }
        }
        children.add(imageView)

        cursor = Cursor.HAND
    }
}