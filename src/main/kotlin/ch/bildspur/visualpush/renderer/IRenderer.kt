package ch.bildspur.visualpush.renderer

import ch.bildspur.timer.TimerTask

interface IRenderer {
    val timerTask: TimerTask

    fun setup()

    fun render()

    fun dispose()
}