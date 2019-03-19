package skeleton.seekbar.entity.draggables

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import skeleton.seekbar.getRectForTextWith
import skeleton.seekbar.getTextBounds

open class DraggableTextCircle(tag: String, percent: Float, color: Int, textColor: Int, val formatter: String = "%.0f") : DraggableCircle(tag, percent, color) {

    protected val textPaint = Paint()
    protected var textRect = Rect()
    protected val textMultiplier = 0.7f

    init {
        textPaint.strokeCap = Paint.Cap.ROUND
        textPaint.typeface = Typeface.MONOSPACE
        textPaint.color = textColor
        textPaint.strokeWidth = 5f
        textPaint.textSize = 100f
    }

    override fun onUpdate(itemWidth: Int, itemHeight: Int) {
        super.onUpdate(itemWidth, itemHeight)
        if (textRect.width() == 0) {
            val temp = formatter.format(seekbar.attributes.max)
            textRect.set(textPaint.getRectForTextWith(temp, itemWidth.toFloat(), itemHeight.toFloat(), textMultiplier))
        }
        val text = formatter.format(value.value)
        textPaint.getTextBounds(text, textRect)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(formatter.format(value.value), rect.centerX() - textRect.width() / 2f,
                rect.centerY() + textRect.height() / 2f, textPaint)
    }
}