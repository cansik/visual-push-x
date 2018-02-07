package ch.bildspur.visualpush.effect

import ch.bildspur.postfx.builder.PostFX
import processing.core.PGraphics

class InvertEffect : VisualEffect {
    override fun apply(fx: PostFX, frame: PGraphics) {
            fx.render(frame)
                    .invert()
                    .compose(frame)
    }
}