package ch.bildspur.visualpush.visual.types

import ch.bildspur.visualpush.visual.Visual
import ch.bildspur.visualpush.visual.VisualScheduler
import com.google.gson.annotations.SerializedName

enum class PlayMode(inline val startActionStrategy: (scheduler: VisualScheduler, visual: Visual) -> Unit = { _, _ -> },
                    inline val endActionStrategy: (scheduler: VisualScheduler, visual: Visual) -> Unit = { _, _ -> }) {
    @SerializedName("one-shot")
    OneShot({ s, v ->
        v.reset()
        s.play(v)
    }),

    @SerializedName("hold")
    Hold(
            { s, v ->
                v.reset()
                s.play(v)
            },
            { s, v ->
                s.stop(v)
            }
    ),

    @SerializedName("loop")
    Loop({ s, v ->
        if (v.state.value == VisualState.Ready)
            s.play(v)
        else
            s.stop(v)
    }),

    @SerializedName("continuous-hold")
    ContinuousHold(
            { s, v ->
                s.play(v)
            },
            { s, v ->
                s.pause(v, hide = true)
            }
    )
}