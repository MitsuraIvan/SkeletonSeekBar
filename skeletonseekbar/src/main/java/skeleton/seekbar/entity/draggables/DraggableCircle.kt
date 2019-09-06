package skeleton.seekbar.entity.draggables

import android.graphics.Canvas
import android.graphics.Paint
import skeleton.seekbar.IDragable

open class DraggableCircle(
    tag: String,
    percent: Float,
    color: Int,
    sticky: Boolean = false,
    iSeekBarChangeListener: ((IDragable, SLIDER_GESTURE) -> Unit) = { _, _ -> }
) :
    AbstractDraggable(tag, percent, sticky, iSeekBarChangeListener) {

    val paint = Paint()

    init {
        paint.color = color
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(
            rect.centerX().toFloat(),
            rect.centerY().toFloat(),
            (rect.width() / 2).toFloat(),
            paint
        )
    }
}