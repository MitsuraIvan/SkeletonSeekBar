package mitsura.skeleton.seekbar.entity

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import mitsura.kotlin.utils.DpToPx
import mitsura.skeleton.seekbar.R

open class MitsuraSeekBarAttrs(context: Context, val attrSet: AttributeSet) {

    var itemH: Int
    var itemW: Int

    var gravityY: Float
    var colorLineBg: Paint
    var colorLineActive: Paint
    var min: Float
    var max: Float
    var step: Float
    var draggableDifference: Float

    init {
        val a = context.getTheme().obtainStyledAttributes(attrSet, R.styleable.MitsuraSeekBar, 0, 0)
        try {
            colorLineBg = Paint()
            colorLineActive = Paint()

            itemH = a.getDimensionPixelSize(R.styleable.MitsuraSeekBar_itemH, 30.DpToPx())
            itemW = a.getDimensionPixelSize(R.styleable.MitsuraSeekBar_itemW, 30.DpToPx())
            gravityY = a.getFloat(R.styleable.MitsuraSeekBar_gravityY, 0.5f)
            val lineStroke = a.getFloat(R.styleable.MitsuraSeekBar_lineStroke, 10f)

            colorLineBg.strokeWidth = lineStroke
            colorLineBg.color = a.getInteger(R.styleable.MitsuraSeekBar_colorLineBg, Color.WHITE)

            colorLineActive.strokeWidth = lineStroke
            colorLineActive.color = a.getInteger(R.styleable.MitsuraSeekBar_colorLineActive, Color.WHITE)

            min = a.getFloat(R.styleable.MitsuraSeekBar_min, 0f)
            max = a.getFloat(R.styleable.MitsuraSeekBar_max, 100f)
            step = a.getFloat(R.styleable.MitsuraSeekBar_step, 1f)
            draggableDifference = a.getFloat(R.styleable.MitsuraSeekBar_draggableDifference, 0f)
        } finally {
            a.recycle()
        }
    }
}