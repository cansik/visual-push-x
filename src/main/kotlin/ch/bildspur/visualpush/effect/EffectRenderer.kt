package ch.bildspur.visualpush.effect

import ch.bildspur.visualpush.util.centerImage
import ch.bildspur.visualpush.util.centerImageAdjusted
import ch.bildspur.visualpush.util.draw
import ch.bildspur.visualpush.visual.Visual
import processing.core.PApplet
import processing.core.PConstants
import processing.core.PGraphics

class EffectRenderer(private val applet : PApplet, private val width : Int, private val height : Int) {
    private lateinit var buffer : PGraphics

    fun setup()
    {
        buffer = applet.createGraphics(width, height, PConstants.P2D)
    }

    fun preRender(visual : Visual) : PGraphics
    {
        buffer.draw {
            clearBuffer()
            it.image(visual.frame, 0f, 0f, it.width.toFloat(), it.height.toFloat())
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