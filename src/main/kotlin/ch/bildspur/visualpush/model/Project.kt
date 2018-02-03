package ch.bildspur.visualpush.model

import ch.bildspur.visualpush.Sketch
import ch.bildspur.visualpush.ui.properties.BooleanParameter
import ch.bildspur.visualpush.ui.properties.IntParameter
import ch.bildspur.visualpush.ui.properties.StringParameter
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
    var highResMode = DataModel(true)

    @Expose
    @BooleanParameter("High FPS Mode*")
    var highFPSMode = DataModel(true)

    @Expose
    @BooleanParameter("Fullscreen Mode*")
    var isFullScreenMode = DataModel(false)

    @Expose
    @IntParameter("Fullscreen Display*")
    var fullScreenDisplay = DataModel(0)
}