package mitsura.skeletonseekbar

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import mitsuru.msb.view.SkeletonSeekBar
import skeleton.seekbar.entity.draggables.DraggableCircle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val seekBar = findViewById<SkeletonSeekBar>(R.id.seekbar)

        seekBar.addSlider(DraggableCircle("TAG1", 0f, Color.WHITE, true) { slider, event ->
            Log.d(
                "logsystem",
                "slider ${slider.getDraggableValueWrapper().value}tag moved to value: $event"
            )
        })
    }
}
