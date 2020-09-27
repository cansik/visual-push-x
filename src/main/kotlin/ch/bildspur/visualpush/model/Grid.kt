package ch.bildspur.visualpush.model

import ch.bildspur.event.Event
import ch.bildspur.model.DataModel
import ch.bildspur.visualpush.model.visual.VisualGrid
import ch.bildspur.visualpush.model.visual.VisualEvent
import ch.bildspur.visualpush.visual.Visual
import com.google.gson.annotations.Expose

class Grid : VisualGrid {
    override val onVisualPlayed = Event<VisualEvent>()
    override val onVisualPaused = Event<VisualEvent>()
    override val onVisualStopped = Event<VisualEvent>()

    override val onVisualAdded = Event<VisualEvent>()
    override val onVisualRemoved = Event<VisualEvent>()

    @Expose
    override val width = DataModel(8)

    @Expose
    override val height = DataModel(8)

    @Expose
    private lateinit var visuals: Array<Visual?>

    init {
        width.onChanged += {
            createGrid()
        }
        height.onChanged += {
            createGrid()
        }

        width.fire()
    }

    override fun play(x: Int, y: Int) {
        get(x, y)?.let {
            onVisualPlayed(VisualEvent(it, x, y))
        }
    }


    override fun pause(x: Int, y: Int) {
        get(x, y)?.let {
            onVisualPaused(VisualEvent(it, x, y))
        }
    }


    override fun stop(x: Int, y: Int) {
        get(x, y)?.let {
            onVisualStopped(VisualEvent(it, x, y))
        }
    }

    override fun get(x : Int, y : Int) : Visual?
    {
        return visuals[x + (height.value * y)]
    }

    override fun add(visual : Visual, x : Int, y : Int)
    {
        visuals[x + (height.value * y)] = visual
        onVisualAdded(VisualEvent(visual, x, y))
    }

    override fun remove(x : Int, y : Int)
    {
        val visual = get(x, y)
        visuals[x + (height.value * y)] = null

        visual?.let {
            onVisualRemoved(VisualEvent(visual, x, y))
        }
    }

    private fun createGrid() {
        visuals = arrayOfNulls(width.value * height.value)
    }

    override fun iterator(): Iterator<Visual> {
        return GridIterator(this)
    }

    class GridIterator(private val grid: Grid) : Iterator<Visual> {
        private var index = -1

        override fun hasNext(): Boolean {
            return (seekNext() < grid.visuals.size)
        }

        override fun next(): Visual {
            index = seekNext()
            return grid.visuals[index]!!
        }

        private fun seekNext(): Int {
            var i = index + 1
            while (i < grid.visuals.size && grid.visuals[i] == null)
                i++
            return i
        }
    }
}