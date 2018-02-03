package ch.bildspur.visualpush.model

import ch.bildspur.visualpush.visual.Visual
import com.google.gson.annotations.Expose
import java.util.concurrent.atomic.AtomicReferenceArray

class Grid {
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
}