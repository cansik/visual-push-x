package ch.bildspur.visualpush.configuration

import ch.bildspur.model.DataModel
import ch.bildspur.visualpush.visual.GLVisual
import ch.bildspur.visualpush.visual.Visual
import com.google.gson.InstanceCreator
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class VisualInstanceCreator: InstanceCreator<Visual> {
    override fun createInstance(type: Type): Visual {
        val typeParameters = (type as ParameterizedType).actualTypeArguments
        val defaultValue = typeParameters[0]
        return GLVisual()
    }
}