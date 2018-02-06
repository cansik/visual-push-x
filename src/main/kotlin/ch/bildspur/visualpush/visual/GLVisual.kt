package ch.bildspur.visualpush.visual

import ch.bildspur.visualpush.visual.types.PlayType
import processing.core.PApplet
import processing.core.PImage
import processing.video.Movie
import java.nio.file.Path

class GLVisual(val applet: PApplet, val path : Path) : Visual() {
    private lateinit var movie: Movie

    private lateinit var actualPreview: PImage
    private lateinit var actualFrame: PImage

    override val previewImage: PImage
        get() = actualPreview

    override val frame: PImage
        get() = actualFrame

    override fun init() {
        movie = Movie(applet, path.toString())

        actualFrame = movie
        generatePreview()
    }

    override fun update() {
        saveRead()
    }

    override fun play() {
        when (this.playType.value) {
            PlayType.LOOP -> movie.loop()
            PlayType.ONE_SHOT -> movie.play()
            PlayType.HOLD -> movie.play()
        }
    }

    override fun pause() {
        movie.pause()
    }

    override fun reset() {
        movie.jump(0f)
    }

    override fun dispose() {
        movie.dispose()
    }

    private fun generatePreview() {
        movie.play()
        saveRead()
        movie.jump(movie.time() / 2f)
        saveRead()
        actualPreview = movie.copy()
        movie.stop()
    }

    private fun saveRead() {
        try {
            movie.read()
        } catch (ex: Exception) {
            error("Movie Read Ex: ${ex.message}")
        }
    }
}