package ch.bildspur.visualpush.configuration

import ch.bildspur.visualpush.visual.GLVisual
import ch.bildspur.visualpush.visual.Visual
import com.github.salomonbrys.kotson.get
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.nio.file.Paths

class VisualTypeAdapter : JsonDeserializer<Visual?> {
    @Throws(JsonParseException::class)
    override fun deserialize(jsonElement: JsonElement, type: Type?, context: JsonDeserializationContext?): Visual {
        // todo: implement loading of different visual types
        val visual = context!!.deserialize<GLVisual>(jsonElement, GLVisual::class.java)
        //val path = Paths.get(jsonElement["path"].asString)
        //visual.path = path
        return visual
    }
}