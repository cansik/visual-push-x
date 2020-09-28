package ch.bildspur.visualpush.visual

import ch.bildspur.model.DataModel
import ch.bildspur.visualpush.model.Project
import ch.bildspur.visualpush.visual.types.VisualState
import java.util.*

class VisualScheduler(project : DataModel<Project>) {
    val visuals = TreeSet<Visual>(compareBy { it.zIndex.value })

    fun setup()
    {

    }

    fun update()
    {
        // todo: implement clip play limit
        visuals.forEach{ it.update() }
        visuals.removeIf { it.state.value == VisualState.Ready || it.state.value == VisualState.Disposed }
    }

    fun actionStarted(visual: Visual)
    {
        visual.playMode.value.startActionStrategy(this, visual)
    }

    fun actionEnded(visual: Visual)
    {
        visual.playMode.value.endActionStrategy(this, visual)
    }

    fun play(visual: Visual) {
        visual.play()
        visuals.add(visual)
    }

    fun pause(visual: Visual, hide: Boolean = false) {
        visual.pause()

        if(hide) {
            visuals.remove(visual)
        }
    }

    fun stop(visual: Visual)
    {
        visual.stop()
        visuals.remove(visual)
    }
}