package skeleton.seekbar.entity

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import skeleton.seekbar.DpToPx
import skeleton.seekbar.R

open class SkeletonSeekBarAttrs {

    val itemH: Int
    val itemW: Int

    val gravityY: Float
    val min: Float
    val max: Float
    val step: Float
    val draggableDifference: Float

    val respectMarginToDrawInsideContainer: Boolean

    val colorLineBg: Paint = Paint()
    val colorLineActive: Paint = Paint()

    constructor(
            itemH: Int,
            itemW: Int,
            gravityY: Float,

            min: Float,
            max: Float,
            step: Float,
            draggableDifference: Float,
            respectMarginToDrawInsideContainer: Boolean,

            lineStroke: Float,

            lineColor: Int,
            lineActiveColor: Int,
    ) {
        this.itemH = itemH
        this.itemW = itemW
        this.gravityY = gravityY

        this.min = min
        this.max = max
        this.step = step
        this.draggableDifference = draggableDifference
        this.respectMarginToDrawInsideContainer = respectMarginToDrawInsideContainer

        this.colorLineBg.strokeWidth = lineStroke
        this.colorLineBg.color = lineColor

        this.colorLineActive.strokeWidth = lineStroke
        this.colorLineActive.color = lineActiveColor
    }

    constructor(context: Context, attrSet: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(attrSet, R.styleable.SkeletonSeekBar, 0, 0)
        try {

            itemH = a.getDimensionPixelSize(R.styleable.SkeletonSeekBar_itemH, 30.DpToPx())
            itemW = a.getDimensionPixelSize(R.styleable.SkeletonSeekBar_itemW, 30.DpToPx())
            gravityY = a.getFloat(R.styleable.SkeletonSeekBar_gravityY, 0.5f)

            min = a.getFloat(R.styleable.SkeletonSeekBar_min, 0f)
            max = a.getFloat(R.styleable.SkeletonSeekBar_max, 100f)
            step = a.getFloat(R.styleable.SkeletonSeekBar_step, 1f)
            draggableDifference = a.getFloat(R.styleable.SkeletonSeekBar_draggableDifference, 0f)
            respectMarginToDrawInsideContainer = a.getBoolean(R.styleable.SkeletonSeekBar_respectRequiredMargin, true)

            val lineStroke = a.getFloat(R.styleable.SkeletonSeekBar_lineStroke, 10f)

            colorLineBg.strokeWidth = lineStroke
            colorLineBg.color = a.getInteger(R.styleable.SkeletonSeekBar_colorLineBg, Color.WHITE)

            colorLineActive.strokeWidth = lineStroke
            colorLineActive.color = a.getInteger(R.styleable.SkeletonSeekBar_colorLineActive, Color.WHITE)
        } finally {
            a.recycle()
        }
    }
}