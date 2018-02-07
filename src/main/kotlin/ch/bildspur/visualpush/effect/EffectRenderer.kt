package ch.bildspur.visualpush.effect

import ch.bildspur.postfx.builder.PostFX
import ch.bildspur.postfx.pass.InvertPass
import ch.bildspur.postfx.pass.SobelPass
import ch.bildspur.visualpush.util.draw
import ch.bildspur.visualpush.visual.Visual
import processing.core.PApplet
import processing.core.PConstants
import processing.core.PGraphics

class EffectRenderer(private val applet : PApplet, private val width : Int, private val height : Int) {
    private lateinit var buffer : PGraphics
    private lateinit var fx : PostFX

    fun setup()
    {
        buffer = applet.createGraphics(width, height, PConstants.P2D)
        fx = PostFX(applet, width, height)

        // preload effects
        fx.preload(InvertPass::class.java)
    }

    fun preRender(visual : Visual) : PGraphics
    {
        buffer.draw {
            clearBuffer()
            it.image(visual.frame, 0f, 0f)
        }

        // apply effects
        visual.effects.forEach {
            it.apply(fx, buffer)
        }

        return buffer
    }

    fun dispose()
    {

    }

    private fun clearBuffer()
    {
        buffer.background(0f, 0f)
    }
}