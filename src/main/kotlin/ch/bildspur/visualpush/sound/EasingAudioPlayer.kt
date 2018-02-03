package ch.bildspur.visualpush.sound

import ch.bildspur.visualpush.model.easing.EasingFloat
import ch.bildspur.visualpush.model.easing.EasingObject
import ddf.minim.AudioPlayer

class EasingAudioPlayer(val player: AudioPlayer,
                        value: Float = DEFAULT_GAIN,
                        target: Float = DEFAULT_GAIN,
                        easing: Float = 0.01f) : EasingObject {
    companion object {
        @JvmStatic
        val MUTED_GAIN = -35f
        val DEFAULT_GAIN = 0.0f
    }

    val volume = EasingFloat(easing)

    init {
        volume.value = value
        volume.target = target

        player.gain = volume.value
    }

    override fun update() {
        volume.update()
        this.player.gain = volume.value
    }
}