package ch.bildspur.visualpush.visual

import java.util.*

class VisualScheduler {
    val visuals = TreeSet<Visual>(compareBy { it.zIndex.value })

    fun setup()
    {

    }

    fun update()
    {
        visuals.forEach{ it.update() }
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