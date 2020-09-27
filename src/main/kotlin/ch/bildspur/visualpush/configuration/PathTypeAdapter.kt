package ch.bildspur.visualpush.configuration

import com.google.gson.*
import java.lang.reflect.Type
import java.nio.file.Path
import java.nio.file.Paths


class PathTypeAdapter : JsonDeserializer<Path?>, JsonSerializer<Path?> {
    @Throws(JsonParseException::class)
    override fun deserialize(jsonElement: JsonElement, type: Type?, jsonDeserializationContext: JsonDeserializationContext?): Path {
        return Paths.get(jsonElement.asString)
    }

    override fun serialize(src: Path?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src.toString())
    }
}