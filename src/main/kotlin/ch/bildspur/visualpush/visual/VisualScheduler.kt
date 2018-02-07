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

    fun play(visual: Visual)
    {
        visual.play()
        visuals.add(visual)
    }

    fun pause(visual: Visual) {
        visual.pause()
    }

    fun stop(visual: Visual)
    {
        visual.stop()
        visuals.remove(visual)
    }
}