package ch.bildspur.visualpush.ui

import ch.bildspur.visualpush.Sketch
import ch.bildspur.visualpush.configuration.ConfigurationController
import ch.bildspur.visualpush.model.AppConfig
import ch.bildspur.visualpush.model.DataModel
import ch.bildspur.visualpush.model.Project
import ch.bildspur.visualpush.ui.properties.PropertiesControl
import ch.bildspur.visualpush.ui.util.UITask
import ch.bildspur.visualpush.visual.GLVisual
import ch.bildspur.visualpush.visual.types.PlayType
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ProgressIndicator
import javafx.scene.control.TitledPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.stage.FileChooser
import javafx.stage.Stage
import processing.core.PApplet
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.concurrent.thread


class PrimaryView {
    lateinit var primaryStage: Stage

    @FXML lateinit var root: BorderPane

    val configuration = ConfigurationController()

    val propertiesControl = PropertiesControl()

    lateinit var appConfig: AppConfig

    val project = DataModel(Project())

    lateinit var sketch: Sketch

    @FXML lateinit var gridPane : GridPane

    @FXML lateinit var propertiesPane: TitledPane

    @FXML lateinit var statusLabel: Label

    @FXML lateinit var progressIndicator: ProgressIndicator

    @FXML lateinit var iconView: ImageView

    private val appIcon = Image(javaClass.getResourceAsStream("images/icon.png"))

    init {
    }

    fun setupView() {
        propertiesPane.content = propertiesControl

        // setup ui task
        UITask.status.addListener { o -> statusLabel.text = UITask.status.value }
        UITask.running.addListener { o -> progressIndicator.isVisible = UITask.running.value }

        // setup ui
        UITask.run({
            // set app icon
            iconView.image = appIcon

            // for updating the property view
            propertiesControl.propertyChanged += {
                updateUI()
            }

            // load app config
            appConfig = configuration.loadAppConfig()

            // create or load configuration
            if (Files.exists(Paths.get(appConfig.projectFile)) && !Files.isDirectory(Paths.get(appConfig.projectFile)))
                project.value = configuration.loadProject(appConfig.projectFile)
            else
                project.value = Project()

            // setup grid
            for (y in 0 until project.value.grid.height.value) {
                for (x in 0 until project.value.grid.width.value) {
                    gridPane.add(Button("hello"), x, y)
                }
            }

            root.center = gridPane

            // start processing
            startProcessing()
        }, { updateUI() }, "startup")
    }

    fun startProcessing() {
        sketch = Sketch()

        project.onChanged += {
            sketch.project.value = project.value
        }
        project.fire()

        sketch.run()

        // add test data
        project.value.grid.clips[0] = GLVisual(this.sketch, Paths.get("data/eye.mp4"))
        project.value.grid.clips[0].playType.value = PlayType.LOOP
    }

    fun updateUI() {
    }

    fun newProject(e: ActionEvent) {
        // show selection dialog
        UITask.run({
            appConfig.projectFile = ""
            project.value = Project()

            resetRenderer()
        }, { updateUI() }, "new project")
    }

    fun loadProject(e: ActionEvent) {
        val fileChooser = FileChooser()
        fileChooser.title = "Select project to load"
        fileChooser.initialFileName = ""
        fileChooser.extensionFilters.addAll(
                FileChooser.ExtensionFilter("JSON", "*.json")
        )

        val result = fileChooser.showOpenDialog(primaryStage)

        if (result != null) {
            UITask.run({
                project.value = configuration.loadProject(result.path)
                appConfig.projectFile = result.path
                configuration.saveAppConfig(appConfig)

                resetRenderer()
            }, { updateUI() }, "load project")
        }
    }

    fun resetRenderer() {
        sketch.proposeResetRenderer()
    }

    fun rebuildRenderer() {
        sketch.renderer.forEach { it.setup() }
    }

    fun saveProjectAs(e: ActionEvent) {
        val fileChooser = FileChooser()
        fileChooser.initialFileName = ""
        fileChooser.title = "Save project..."
        fileChooser.extensionFilters.addAll(FileChooser.ExtensionFilter("JSON", "*.json"))

        val result = fileChooser.showSaveDialog(primaryStage)

        if (result != null) {
            UITask.run({
                configuration.saveProject(result.path, project.value)
                appConfig.projectFile = result.path
                configuration.saveAppConfig(appConfig)
            }, { updateUI() }, "save project")
        }
    }

    fun saveProject(e: ActionEvent) {
        if (Files.exists(Paths.get(appConfig.projectFile)) && !Files.isDirectory(Paths.get(appConfig.projectFile))) {
            UITask.run({
                configuration.saveProject(appConfig.projectFile, project.value)
                configuration.saveAppConfig(appConfig)
            }, { updateUI() }, "save project")
        }
    }

    fun showProjectSettings(e: ActionEvent) {
        propertiesControl.initView(project.value)
    }
}
