package ch.bildspur.visualpush.visual.types

import com.google.gson.annotations.SerializedName

enum class PlayType {
    @SerializedName("one-shot")
    ONE_SHOT,

    @SerializedName("hold")
    HOLD,

    @SerializedName("loop")
    LOOP
}