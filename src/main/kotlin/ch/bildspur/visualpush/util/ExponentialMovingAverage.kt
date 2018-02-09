package ch.bildspur.visualpush.util

class ExponentialMovingAverage(private val alpha: Double, var average : Double = Double.NaN) {
    fun add(value: Double) {
        if (average.isNaN()) {
            average = value
        }

        val newValue = average + alpha * (value - average)
        average = newValue
    }

    operator fun plusAssign(value: Double) {
        add(value)
    }
}