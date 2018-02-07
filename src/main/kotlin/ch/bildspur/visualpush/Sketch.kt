package ch.bildspur.visualpush

import ch.bildspur.visualpush.controller.timer.Timer
import ch.bildspur.visualpush.controller.timer.TimerTask
import ch.bildspur.visualpush.model.DataModel
import ch.bildspur.visualpush.model.Project
import ch.bildspur.visualpush.view.IRenderer
import ch.bildspur.visualpush.view.VisualRenderer
import ch.bildspur.postfx.builder.PostFX
import ch.bildspur.visualpush.controller.SyphonController
import ch.bildspur.visualpush.util.*
import ch.bildspur.visualpush.visual.VisualScheduler
import processing.core.PApplet
import processing.core.PConstants
import processing.core.PGraphics
import processing.opengl.PJOGL
import processing.video.Movie




/**
 * Created by cansik on 04.02.17.
 */
class Sketch : PApplet() {
    companion object {
        @JvmStatic
        val HIGH_RES_FRAME_RATE = 60f
        @JvmStatic
        val LOW_RES_FRAME_RATE = 30f

        @JvmStatic
        val WINDOW_WIDTH = 1024
        @JvmStatic
        val WINDOW_HEIGHT = 576

        @JvmStatic
        val CURSOR_HIDING_TIME = 1000L * 5L

        @JvmStatic
        val LOGBOOK_UPDATE_TIME = 1000L * 60L * 10L

        @JvmStatic
        val NAME = "Visual Push X"

        @JvmStatic
        val VERSION = "0.1"

        @JvmStatic lateinit var instance: PApplet

        @JvmStatic
        fun map(value: Double, start1: Double, stop1: Double, start2: Double, stop2: Double): Double {
            return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1))
        }

        @JvmStatic
        fun currentMillis(): Int {
            return instance.millis()
        }
    }

    @Volatile
    private var isInitialised = false

    var fpsOverTime = 0f

    @Volatile
    var isResetRendererProposed = false

    var isRendering = DataModel(true)

    val syphon = SyphonController(this)

    val timer = Timer()

    lateinit var canvas: PGraphics

    var lastCursorMoveTime = 0

    val renderer = mutableListOf<IRenderer>()

    val project = DataModel(Project())

    lateinit var fx: PostFX

    val scheduler = VisualScheduler()

    init {
    }

    fun run()
    {
        super.runSketch()
    }

    override fun settings() {
        if (project.value.isFullScreenMode.value)
            fullScreen(PConstants.P3D, project.value.fullScreenDisplay.value)
        else
            size(WINDOW_WIDTH, WINDOW_HEIGHT, PConstants.P2D)

        PJOGL.profile = 1
        smooth()

        // retina screen
        if (project.value.highResMode.value)
            pixelDensity = 2
    }

    override fun setup() {
        Sketch.instance = this

        frameRate(if (project.value.highFPSMode.value) HIGH_RES_FRAME_RATE else LOW_RES_FRAME_RATE)
        colorMode(HSB, 360f, 100f, 100f)

        project.onChanged += {
            onProjectChanged()
        }
        project.fire()

        fx = PostFX(this)

        // timer for cursor hiding
        timer.addTask(TimerTask(CURSOR_HIDING_TIME, {
            val current = millis()
            if (current - lastCursorMoveTime > CURSOR_HIDING_TIME)
                noCursor()
        }, "CursorHide"))

        // timer for logbook
        timer.addTask(TimerTask(LOGBOOK_UPDATE_TIME, {
            // do nothing
        }))

        // timer for scheduler
        timer.addTask(TimerTask(0, {
            scheduler.update()
        }))

        LogBook.log("Start")
    }

    override fun draw() {
        background(0)

        if (skipFirstFrames())
            return

        // setup long loading controllers
        if (initControllers())
            return

        // reset renderer if needed
        if (isResetRendererProposed)
            resetRenderer()

        canvas.draw {
            it.background(0)

            // render (update timer)
            if (isRendering.value)
                timer.update()
        }

        // output image
        if (project.value.highResMode.value) {
            fx.render(canvas)
                    .bloom(0.0f, 20, 40f)
                    .compose(canvas)
        }

        image(canvas, 0f, 0f)
        syphon.sendImage(canvas)
        drawFPS(g)
    }

    fun onProjectChanged() {
        surface.setTitle("$NAME ($VERSION) - ${project.value.name.value}")

        // setup hooks
        setupHooks()
    }

    fun setupHooks() {
        project.value.grid.onVisualPlayed += {
            scheduler.play(it.visual)
        }

        project.value.grid.onVisualPaused += {
            scheduler.pause(it.visual)
        }

        project.value.grid.onVisualStopped += {
            scheduler.stop(it.visual)
        }
    }

    fun proposeResetRenderer() {
        if (isInitialised) {
            isResetRendererProposed = true
        }
    }

    fun resetRenderer() {
        println("resetting renderer...")

        renderer.forEach {
            timer.taskList.remove(it.timerTask)
            it.dispose()
        }

        renderer.clear()

        // add renderer
        renderer.add(VisualRenderer(canvas, project.value, scheduler))

        isResetRendererProposed = false

        // rebuild
        // setup renderer
        renderer.forEach {
            it.setup()
            timer.addTask(it.timerTask)
        }
    }

    fun resetCanvas() {
        canvas = createGraphics(project.value.outputWidth.value, project.value.outputHeight.value, PConstants.P2D)

        // retina screen
        if (project.value.highResMode.value)
            pixelDensity = 2
    }

    fun skipFirstFrames(): Boolean {
        // skip first two frames
        if (frameCount < 2) {
            textAlign(CENTER, CENTER)
            fill(255)
            textSize(20f)
            text("${Sketch.NAME} is loading...", width / 2f, height / 2f)
            return true
        }

        return false
    }

    fun initControllers(): Boolean {
        if (!isInitialised) {
            resetCanvas()

            syphon.setup()
            scheduler.setup()
            timer.setup()

            initialiseClips()

            prepareExitHandler()

            // setting up renderer
            resetRenderer()

            isInitialised = true
            return true
        }

        return false
    }

    fun initialiseClips()
    {
        project.value.grid.forEach { it.init() }
    }

    fun drawFPS(pg: PGraphics) {
        // draw fps
        fpsOverTime += frameRate
        val averageFPS = fpsOverTime / frameCount.toFloat()

        pg.textAlign(PApplet.LEFT, PApplet.BOTTOM)
        pg.fill(255)
        pg.textSize(12f)
        pg.text("FPS: ${frameRate.format(2)}\nFOT: ${averageFPS.format(2)}", 10f, height - 5f)
    }

    fun prepareExitHandler() {
        Runtime.getRuntime().addShutdownHook(Thread {
            println("shutting down...")
            renderer.forEach { it.dispose() }

            LogBook.log("Stop")
        })
    }

    override fun mouseMoved() {
        super.mouseMoved()
        cursor()
        lastCursorMoveTime = millis()
    }
}