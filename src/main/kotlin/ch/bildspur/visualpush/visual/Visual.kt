package ch.bildspur.visualpush.visual

import ch.bildspur.model.DataModel
import ch.bildspur.ui.properties.EnumParameter
import ch.bildspur.ui.properties.NumberParameter
import ch.bildspur.ui.properties.StringParameter
import ch.bildspur.visualpush.effect.VisualEffect
import ch.bildspur.visualpush.visual.types.BlendMode
import ch.bildspur.visualpush.visual.types.PlayMode
import ch.bildspur.visualpush.visual.types.VisualState
import ch.bildspur.visualpush.visual.types.VisualType
import com.google.gson.annotations.Expose
import processing.core.PApplet
import processing.core.PImage

abstract class Visual {
    lateinit var applet : PApplet

    @Expose
    protected var visualType = VisualType.VIDEO

    @Expose
    @NumberParameter("Z-Index")
    val zIndex = DataModel(0)

    @Expose
    @StringParameter("Name")
    val name = DataModel("Visual")

    @Expose
    @EnumParameter("Play Mode")
    val playMode = DataModel(PlayMode.Loop)

    @Expose
    @EnumParameter("Blend Mode")
    val blendMode = DataModel(BlendMode.BLEND)

    val state = DataModel(VisualState.Ready)

    //@Expose
    val effects = mutableListOf<VisualEffect>()

    abstract val previewImage : PImage

    abstract val frame : PImage

    /**
     * Loads the visual resources.
     */
    open fun init(applet : PApplet) {
        this.applet = applet
    }

    /**
     * Updates every frame.
     */
    abstract fun update()

    /**
     * Plays the visual.
     */
    open fun play()
    {
        state.value = VisualState.Playing
    }

    /**
     * Pauses the visual.
     */
    open fun pause()
    {
        state.value = VisualState.Paused
    }

    /**
     * Resets the visual to it's starting point.
     */
    abstract fun reset()

    /**
     * Diposes the visual resources.
     */
    open fun dispose() {
        state.value = VisualState.Disposed
    }

    /**
     * Pauses and resets the visual.
     */
    fun stop() {
        pause()
        reset()

        state.value = VisualState.Ready
    }
}