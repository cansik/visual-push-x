package ch.bildspur.visualpush.event

/**
 * Created by cansik on 09.06.17.
 */
class Event<T> {
    private val handlers = arrayListOf<(Event<T>.(T) -> Unit)>()

    fun add(handler: Event<T>.(T) -> Unit) : Event<T>.(T) -> Unit
    {
        handlers.add(handler)
        return handler
    }

    fun remove(handler: Event<T>.(T) -> Unit) : Boolean
    {
        return handlers.remove(handler)
    }

    operator fun plusAssign(handler: Event<T>.(T) -> Unit) {
        add(handler)
    }

    operator fun minusAssign(handler: Event<T>.(T) -> Unit)
    {
        remove(handler)
    }

    operator fun invoke(value: T) {
        for (handler in handlers)
            handler(value)
    }

    fun invokeLatest(value: T) {
        handlers.last()(value)
    }

    fun clear() {
        handlers.clear()
    }
}