package ch.bildspur.visualpush.effect

import ch.bildspur.postfx.builder.PostFXBuilder
import processing.core.PGraphics

class InvertEffect : VisualEffect {
    override fun apply(fx: PostFXBuilder, frame: PGraphics) {
            fx.invert()
    }
}