package mitsura.skeleton.seekbar.entity.draggables

import android.graphics.Canvas
import android.graphics.Paint
import mitsura.skeleton.seekbar.entity.draggables.AbstractDraggable

open class DraggableCircle(tag: String, percent: Float, val color: Int) : AbstractDraggable(tag, percent) {

    val paint = Paint()

    init {
        paint.color = color
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(rect.centerX().toFloat(),
                rect.centerY().toFloat(),
                (rect.width() / 2).toFloat(),
                paint)
    }
}