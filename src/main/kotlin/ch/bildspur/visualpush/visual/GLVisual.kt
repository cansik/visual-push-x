package ch.bildspur.visualpush.visual

import ch.bildspur.visualpush.visual.types.PlayMode
import com.google.gson.annotations.Expose
import processing.core.PApplet
import processing.core.PImage
import processing.video.Movie
import java.nio.file.Path

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
        when (this.playMode.value) {
            PlayMode.Loop -> movie.loop()
            PlayMode.OneShot -> movie.play()
            PlayMode.Hold -> movie.play()
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
        println(movie.duration())
        movie.jump(movie.duration() / 2f)
        actualPreview = movie.copy()
        stop()
    }

    private fun saveRead() {
        movie.read()
    }
}