package ch.bildspur.visualpush.visual

import ch.bildspur.visualpush.model.DataModel
import ch.bildspur.visualpush.visual.types.BlendMode
import ch.bildspur.visualpush.visual.types.PlayType
import com.google.gson.annotations.Expose
import processing.core.PGraphics
import processing.core.PImage

abstract class Visual {
    @Expose
    var name = DataModel("Visual")

    @Expose
    var playType = DataModel(PlayType.LOOP)

    @Expose
    var blendMode = DataModel(BlendMode.BLEND)

    abstract val previewImage : PImage

    /**
     * Loads the visual resources.
     */
    abstract fun init()

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

    /**
     * Renders the visual onto the context
     */
    abstract fun render(ctx : PGraphics)
}