package ch.bildspur.visualpush.visual

import ch.bildspur.visualpush.Sketch
import ch.bildspur.visualpush.util.isApproximate
import ch.bildspur.visualpush.visual.types.PlayMode
import ch.bildspur.visualpush.visual.types.VisualState
import com.google.gson.annotations.Expose
import processing.core.PApplet
import processing.core.PImage
import processing.video.Movie
import java.nio.file.Path

class GLVisual() : Visual() {
    private lateinit var movie: Movie
    private lateinit var actualFrame: PImage

    @Expose
    lateinit var path : Path

    override val frame: PImage
        get() = actualFrame

    val hasEnded : Boolean
        get() = movie.time().isApproximate(movie.duration())

    constructor(path : Path) : this() {
        this.path = path
    }

    override fun init(applet: PApplet) {
        super.init(applet)

        movie = Movie(applet, path.toString())
        actualFrame = movie

        generatePreview()
    }

    override fun update() {
        saveRead()

        // check if movie finished
        if(hasEnded) {
            when(playMode.value) {
                PlayMode.Loop -> Unit
                else -> state.value = VisualState.Ready
            }
        }
    }

    override fun play() {
        super.play()
        when (this.playMode.value) {
            PlayMode.Loop -> movie.loop()
            else -> movie.play()
        }
    }

    override fun pause() {
        super.pause()
        movie.pause()
    }

    override fun reset() {
        movie.jump(0f)
    }

    override fun dispose() {
        movie.post()
        movie.dispose()
    }

    private fun generatePreview() {
        // todo: fix preview generation
        movie.play()
        saveRead()
        movie.jump(movie.duration() / 2f)
        saveRead()
        movie.loadPixels()
        movie.updatePixels()
        preview.value = movie.copy()
        movie.stop()
    }

    private fun saveRead() {
        movie.read()
    }
}