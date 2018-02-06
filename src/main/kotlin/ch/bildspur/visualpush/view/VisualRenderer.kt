package ch.bildspur.visualpush.view

import ch.bildspur.visualpush.controller.timer.TimerTask
import ch.bildspur.visualpush.model.Project
import ch.bildspur.visualpush.util.draw
import ch.bildspur.visualpush.visual.Visual
import ch.bildspur.visualpush.visual.VisualScheduler
import processing.core.PGraphics

class VisualRenderer(val ctx: PGraphics, val project : Project, val scheduler: VisualScheduler) : IRenderer {
    private val task = TimerTask(0, { render() }, "VisualRenderer")
    override val timerTask: TimerTask
        get() = task


    override fun setup() {
    }

    override fun render() {
        ctx.draw {
            it.background(0f, 120f, 255f)
            scheduler.visuals.forEach { renderVisual(it) }
        }
    }

    private fun renderVisual(visual : Visual)
    {
        ctx.image(visual.frame, 0f, 0f, ctx.width.toFloat(), ctx.height.toFloat())
    }

    override fun dispose() {
    }
}