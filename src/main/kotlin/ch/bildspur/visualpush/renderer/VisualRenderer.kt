package ch.bildspur.visualpush.renderer

import ch.bildspur.timer.TimerTask
import ch.bildspur.visualpush.effect.EffectRenderer
import ch.bildspur.visualpush.model.Project
import ch.bildspur.visualpush.util.draw
import ch.bildspur.visualpush.visual.Visual
import ch.bildspur.visualpush.visual.VisualScheduler
import processing.core.PGraphics

class VisualRenderer(private val ctx: PGraphics,
                     private val project : Project,
                     private val scheduler: VisualScheduler,
                     private val effectRenderer : EffectRenderer) : IRenderer {
    private val task = TimerTask(0, { render() }, "VisualRenderer")
    override val timerTask: TimerTask
        get() = task

    override fun setup() {
        effectRenderer.setup()
    }

    override fun render() {
        ctx.draw {
            it.background(0f)
        }

        scheduler.visuals.forEach { renderVisual(it) }
    }

    private fun renderVisual(visual : Visual)
    {
        val preRendered = effectRenderer.preRender(visual)

        ctx.draw {
            it.blendMode(visual.blendMode.value.value)
            // todo: center / fit image
            it.image(preRendered, 0f, 0f, it.width.toFloat(), it.height.toFloat())
        }
    }

    override fun dispose() {
        effectRenderer.dispose()
    }
}