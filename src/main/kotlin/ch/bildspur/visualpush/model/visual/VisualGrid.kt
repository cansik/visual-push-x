package ch.bildspur.visualpush.model.visual

import ch.bildspur.event.Event
import ch.bildspur.model.DataModel
import ch.bildspur.visualpush.visual.Visual

interface VisualGrid : Iterable<Visual> {
    val onVisualActionStarted : Event<VisualEvent>
    val onVisualActionEnded  : Event<VisualEvent>

    val onVisualPlayed : Event<VisualEvent>
    val onVisualPaused : Event<VisualEvent>
    val onVisualStopped : Event<VisualEvent>

    val onVisualAdded : Event<VisualEvent>
    val onVisualRemoved : Event<VisualEvent>

    val width : DataModel<Int>
    val height : DataModel<Int>

    fun actionStarted(x : Int, y: Int)
    fun actionEnded(x : Int, y: Int)

    fun play(x: Int, y: Int)
    fun pause(x: Int, y: Int)
    fun stop(x: Int, y: Int)

    fun get(x : Int, y : Int) : Visual?
    fun add(visual : Visual, x : Int, y : Int)
    fun remove(x : Int, y : Int)
}