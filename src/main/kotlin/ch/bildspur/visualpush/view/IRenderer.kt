package ch.bildspur.visualpush.view

import ch.bildspur.visualpush.controller.timer.TimerTask

interface IRenderer {
    val timerTask: TimerTask

    fun setup()

    fun render()

    fun dispose()
}