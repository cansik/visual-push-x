package ch.bildspur.visualpush.visual

import ch.bildspur.visualpush.model.DataModel
import ch.bildspur.visualpush.visual.types.BlendMode
import ch.bildspur.visualpush.visual.types.PlayType
import com.google.gson.annotations.Expose
import processing.core.PGraphics
import processing.core.PImage

abstract class Visual {
    @Expose
    var zIndex = DataModel(0)

    @Expose
    var name = DataModel("Visual")

    @Expose
    var playType = DataModel(PlayType.LOOP)

    @Expose
    var blendMode = DataModel(BlendMode.BLEND)

    abstract val previewImage : PImage

    abstract val frame : PImage

    /**
     * Loads the visual resources.
     */
    abstract fun init()

    /**
     * Updates every frame.
     */
    abstract fun update()

    /**
     * Plays the visual.
     */
    abstract fun play()

    /**
     * Pauses the visual.
     */
    abstract fun pause()

    /**
     * Resets the visual to it's starting point.
     */
    abstract fun reset()

    /**
     * Diposes the visual resources.
     */
    abstract fun dispose()

    /**
     * Pauses and resets the visual.
     */
    fun stop() {
        pause()
        reset()
    }
}