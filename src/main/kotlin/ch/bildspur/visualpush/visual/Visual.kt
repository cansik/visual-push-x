package ch.bildspur.visualpush.visual

import ch.bildspur.visualpush.effect.VisualEffect
import ch.bildspur.visualpush.model.DataModel
import ch.bildspur.visualpush.visual.types.BlendMode
import ch.bildspur.visualpush.visual.types.PlayType
import com.google.gson.annotations.Expose
import processing.core.PGraphics
import processing.core.PImage

abstract class Visual {
    @Expose
    val zIndex = DataModel(0)

    @Expose
    val name = DataModel("Visual")

    @Expose
    val playType = DataModel(PlayType.LOOP)

    @Expose
    val blendMode = DataModel(BlendMode.BLEND)

    @Expose
    val isPlaying = DataModel(false)

    @Expose
    val effects = mutableListOf<VisualEffect>()

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
    open fun play()
    {
        isPlaying.value = true
    }

    /**
     * Pauses the visual.
     */
    open fun pause()
    {
        isPlaying.value = false
    }

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