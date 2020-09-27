package ch.bildspur.visualpush.visual.types

import com.google.gson.annotations.SerializedName

enum class PlayMode {
    @SerializedName("one-shot")
    ONE_SHOT,

    @SerializedName("hold")
    HOLD,

    @SerializedName("loop")
    LOOP
}