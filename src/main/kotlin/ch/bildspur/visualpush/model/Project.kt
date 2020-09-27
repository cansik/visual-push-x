package ch.bildspur.visualpush.model

import ch.bildspur.model.DataModel
import ch.bildspur.ui.properties.BooleanParameter
import ch.bildspur.ui.properties.NumberParameter
import ch.bildspur.ui.properties.StringParameter
import ch.bildspur.visualpush.Sketch
import com.google.gson.annotations.Expose

/**
 * Created by cansik on 11.07.17.
 */
class Project {
    @Expose
    @StringParameter("Name")
    var name = DataModel("${Sketch.NAME} Project")

    @Expose
    @BooleanParameter("High Res Mode*")
    var highResMode = DataModel(false)

    @Expose
    @BooleanParameter("High FPS Mode*")
    var highFPSMode = DataModel(true)

    @Expose
    @BooleanParameter("Fullscreen Mode*")
    var isFullScreenMode = DataModel(false)

    @Expose
    @NumberParameter("Fullscreen Display*")
    var fullScreenDisplay = DataModel(0)

    @Expose
    @NumberParameter("Output Width", "px")
    var outputWidth = DataModel(1280)

    @Expose
    @NumberParameter("Output Height", "px")
    var outputHeight = DataModel(720)

    @Expose
    var grid = Grid()


}