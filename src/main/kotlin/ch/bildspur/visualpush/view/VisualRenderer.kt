package ch.bildspur.visualpush.view

import ch.bildspur.visualpush.controller.timer.TimerTask
import ch.bildspur.visualpush.model.Project
import ch.bildspur.visualpush.util.centerImage
import ch.bildspur.visualpush.util.centerImageAdjusted
import ch.bildspur.visualpush.util.draw
import ch.bildspur.visualpush.visual.Visual
import ch.bildspur.visualpush.visual.VisualScheduler
import processing.core.PGraphics

class VisualRenderer(private val ctx: PGraphics, val project : Project, private val scheduler: VisualScheduler) : IRenderer {
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
        ctx.centerImageAdjusted(visual.frame)
    }

    override fun dispose() {
    }
}