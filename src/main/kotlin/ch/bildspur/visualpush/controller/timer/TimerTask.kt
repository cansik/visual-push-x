package ch.bildspur.visualpush.controller.timer

/**
 * Created by cansik on 12.07.17.
 */
class TimerTask(val interval: Long, val block: (task: TimerTask) -> Unit, val name: String = "") {
    var finished = false
    var lastMillis = 0L
}