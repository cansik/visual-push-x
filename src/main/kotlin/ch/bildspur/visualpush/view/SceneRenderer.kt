package ch.bildspur.visualpush.view

import ch.bildspur.visualpush.controller.timer.TimerTask
import ch.bildspur.visualpush.model.Project
import ch.bildspur.visualpush.util.draw
import processing.core.PGraphics

class SceneRenderer(val g: PGraphics, val project : Project) : IRenderer {
    private val task = TimerTask(0, { render() }, "SceneRenderer")
    override val timerTask: TimerTask
        get() = task


    override fun setup() {
        g.draw {
            it.background(55)
        }
    }

    override fun render() {

    }

    override fun dispose() {
    }
}