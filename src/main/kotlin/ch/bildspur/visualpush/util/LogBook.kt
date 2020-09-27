package ch.bildspur.visualpush.util

import ch.bildspur.visualpush.Sketch
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LogBook {
    private var separator: String = ";"
    private val logFileDirectory = "logs"
    private var logFileName = "${Sketch.URI}.log"
    private var firstTime = true

    fun log(message: String, vararg attributes: Any = emptyArray()) {
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val dateTime = current.format(formatter)

        val entries = listOf(dateTime, message, *attributes.map { it.toString() }.toTypedArray())
        val logEntry = entries.joinToString(separator)

        val path = Paths.get(logFileDirectory, logFileName)

        // first time startup fix
        if(firstTime) {
            if(!Files.exists(path)) {
                Files.createDirectories(path.parent)
                Files.createFile(path)
            }
            firstTime = false
        }

        File(path.toUri()).appendText("$logEntry\n")
    }
}