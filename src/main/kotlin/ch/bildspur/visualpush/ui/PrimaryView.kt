package ch.bildspur.visualpush.ui

import ch.bildspur.configuration.ConfigurationController
import ch.bildspur.model.DataModel
import ch.bildspur.ui.fx.PropertiesControl
import ch.bildspur.visualpush.Sketch
import ch.bildspur.visualpush.effect.InvertEffect
import ch.bildspur.visualpush.model.AppConfig
import ch.bildspur.visualpush.model.Project
import ch.bildspur.visualpush.model.visual.VisualGrid
import ch.bildspur.visualpush.ui.control.grid.EmptyView
import ch.bildspur.visualpush.ui.control.grid.VisualView
import ch.bildspur.visualpush.ui.util.UITask
import ch.bildspur.visualpush.visual.GLVisual
import ch.bildspur.visualpush.visual.types.PlayMode
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.ProgressIndicator
import javafx.scene.control.TitledPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.stage.FileChooser
import javafx.stage.Stage
import java.nio.file.Files
import java.nio.file.Paths


class PrimaryView {
    lateinit var primaryStage: Stage

    @FXML
    lateinit var root: BorderPane

    val configuration = ConfigurationController(Sketch.NAME, "bildspur", Sketch.URI)

    val propertiesControl = PropertiesControl()

    lateinit var appConfig: AppConfig

    val project = DataModel(Project())

    lateinit var sketch: Sketch

    @FXML
    lateinit var gridPane: GridPane

    @FXML
    lateinit var propertiesPane: TitledPane

    @FXML
    lateinit var statusLabel: Label

    @FXML
    lateinit var progressIndicator: ProgressIndicator

    @FXML
    lateinit var iconView: ImageView

    private val appIcon = Image(javaClass.getResourceAsStream("images/icon.png"))

    private lateinit var grid : VisualGrid

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
                project.value = configuration.loadData(Paths.get(appConfig.projectFile))
            else
                project.value = Project()

            // setup grid
            grid = project.value.grid
            setupGrid()
            project.value.grid.onVisualAdded += {
                setupGrid()
            }
            project.value.grid.onVisualRemoved += {
                setupGrid()
            }

            root.center = gridPane

            // start processing
            startProcessing()
        }, { updateUI() }, "startup")
    }

    fun setupGrid() {
        gridPane.children.clear()
        for (y in 0 until project.value.grid.height.value) {
            for (x in 0 until project.value.grid.width.value) {
                val visual = project.value.grid.get(x, y)

                if (visual != null) {
                    gridPane.add(VisualView(grid, visual, x, y), x, y)
                } else {
                    gridPane.add(EmptyView(), x, y)
                }
            }
        }
    }

    fun startProcessing() {
        sketch = Sketch()

        project.onChanged += {
            sketch.project.value = project.value
            grid = project.value.grid
        }
        project.fire()

        sketch.run()

        // add test data
        val testVisual = GLVisual(Paths.get("data/eye.mp4"))
        testVisual.playType.value = PlayMode.LOOP
        testVisual.effects.add(InvertEffect())
        //testVisual.blendMode.value = BlendMode.ADD

        project.value.grid.add(testVisual, 0, 0)
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
                project.value = configuration.loadData(result.toPath())
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
                configuration.saveData(result.toPath(), project.value)
                appConfig.projectFile = result.path
                configuration.saveAppConfig(appConfig)
            }, { updateUI() }, "save project")
        }
    }

    fun saveProject(e: ActionEvent) {
        if (Files.exists(Paths.get(appConfig.projectFile)) && !Files.isDirectory(Paths.get(appConfig.projectFile))) {
            UITask.run({
                configuration.saveData(Paths.get(appConfig.projectFile), project.value)
                configuration.saveAppConfig(appConfig)
            }, { updateUI() }, "save project")
        } else {
            saveProjectAs(e)
        }
    }

    fun showProjectSettings(e: ActionEvent) {
        propertiesControl.initView(project.value)
    }
}
