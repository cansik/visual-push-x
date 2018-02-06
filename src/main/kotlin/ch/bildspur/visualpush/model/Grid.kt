package ch.bildspur.visualpush.model

import ch.bildspur.visualpush.visual.Visual
import com.google.gson.annotations.Expose
import java.util.concurrent.atomic.AtomicReferenceArray

class Grid : Iterable<Visual> {
    @Expose
    var width = DataModel(8)

    @Expose
    var height = DataModel(8)

    @Expose
    lateinit var clips : AtomicReferenceArray<Visual>

    init {
        width.onChanged += {
            createGrid()
        }
        height.onChanged += {
            createGrid()
        }

        width.fire()
    }

    private fun createGrid()
    {
        clips = AtomicReferenceArray(width.value * height.value)
    }

    override fun iterator(): Iterator<Visual> {
        return GridIterator(this)
    }

    class GridIterator(private val grid : Grid) : Iterator<Visual>
    {
        private var index = -1

        override fun hasNext(): Boolean {
            return (seekNext() < grid.clips.length())
        }

        override fun next(): Visual {
            index = seekNext()
            return grid.clips[index]
        }

        private fun seekNext() : Int
        {
            var i = index + 1
            while (i < grid.clips.length() && grid.clips[i] == null)
                i++
            return i
        }
    }
}