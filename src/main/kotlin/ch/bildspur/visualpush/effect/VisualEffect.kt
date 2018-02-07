package ch.bildspur.visualpush.effect

import ch.bildspur.postfx.builder.PostFX
import processing.core.PGraphics

interface VisualEffect {
    fun apply(fx : PostFX, frame : PGraphics)
}