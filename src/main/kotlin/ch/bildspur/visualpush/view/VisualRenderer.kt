package ch.bildspur.visualpush.view

import ch.bildspur.visualpush.controller.timer.TimerTask
import ch.bildspur.visualpush.model.Project
import ch.bildspur.visualpush.util.draw
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
        }
    }

    override fun dispose() {
    }
}