package ch.bildspur.visualpush.effect

import ch.bildspur.postfx.builder.PostFXBuilder
import processing.core.PGraphics

interface VisualEffect {
    fun apply(fx : PostFXBuilder, frame : PGraphics)
}