package ch.bildspur.visualpush.visual

import ch.bildspur.visualpush.visual.types.PlayMode
import com.google.gson.annotations.Expose
import processing.core.PApplet
import processing.core.PImage
import processing.video.Movie
import java.nio.file.Path
import java.nio.file.Paths

class GLVisual() : Visual() {
    private lateinit var movie: Movie

    private lateinit var actualPreview: PImage
    private lateinit var actualFrame: PImage

    @Expose
    lateinit var path : Path

    override val previewImage: PImage
        get() = actualPreview

    override val frame: PImage
        get() = actualFrame

    constructor(path : Path) : this() {
        this.path = path
    }

    override fun init(applet: PApplet) {
        super.init(applet)

        movie = Movie(applet, path.toString())
        actualFrame = movie

        //generatePreview()
    }

    override fun update() {
        saveRead()
    }

    override fun play() {
        super.play()
        when (this.playType.value) {
            PlayMode.LOOP -> movie.loop()
            PlayMode.ONE_SHOT -> movie.play()
            PlayMode.HOLD -> movie.play()
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
        saveRead()
        movie.play()
        saveRead()
        println(movie.duration())
        movie.jump(movie.duration() / 2f)
        saveRead()
        saveRead()
        actualPreview = movie.copy()
        stop()
    }

    private fun saveRead() {
        try {
            movie.read()
        } catch (ex: Exception) {
            error("Movie Read Ex: ${ex.message}")
        }
    }
}