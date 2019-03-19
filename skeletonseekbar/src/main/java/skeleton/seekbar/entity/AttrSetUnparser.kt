package skeleton.seekbar.entity

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import skeleton.seekbar.DpToPx
import skeleton.seekbar.R

open class SkeletonSeekBarAttrs(context: Context, val attrSet: AttributeSet) {

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
        val a = context.theme.obtainStyledAttributes(attrSet, R.styleable.SkeletonSeekBar, 0, 0)
        try {
            colorLineBg = Paint()
            colorLineActive = Paint()

            itemH = a.getDimensionPixelSize(R.styleable.SkeletonSeekBar_itemH, 30.DpToPx())
            itemW = a.getDimensionPixelSize(R.styleable.SkeletonSeekBar_itemW, 30.DpToPx())
            gravityY = a.getFloat(R.styleable.SkeletonSeekBar_gravityY, 0.5f)
            val lineStroke = a.getFloat(R.styleable.SkeletonSeekBar_lineStroke, 10f)

            colorLineBg.strokeWidth = lineStroke
            colorLineBg.color = a.getInteger(R.styleable.SkeletonSeekBar_colorLineBg, Color.WHITE)

            colorLineActive.strokeWidth = lineStroke
            colorLineActive.color = a.getInteger(R.styleable.SkeletonSeekBar_colorLineActive, Color.WHITE)

            min = a.getFloat(R.styleable.SkeletonSeekBar_min, 0f)
            max = a.getFloat(R.styleable.SkeletonSeekBar_max, 100f)
            step = a.getFloat(R.styleable.SkeletonSeekBar_step, 1f)
            draggableDifference = a.getFloat(R.styleable.SkeletonSeekBar_draggableDifference, 0f)
        } finally {
            a.recycle()
        }
    }
}