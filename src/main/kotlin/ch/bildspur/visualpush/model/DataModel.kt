package ch.bildspur.visualpush.model

import ch.bildspur.visualpush.event.Event
import com.google.gson.annotations.Expose


/**
 * Created by cansik on 09.06.17.
 */
class DataModel<T>(@Expose @Volatile private var dataValue: T) {
    val onChanged = Event<T>()

    var value: T
        get() = this.dataValue
        set(value) {
            val oldValue = dataValue
            dataValue = value

            // fire event if changed
            if (dataValue != oldValue)
                fire()
        }

    fun fire() {
        onChanged(dataValue)
    }

    fun fireLatest() {
        onChanged.invokeLatest(dataValue)
    }
}