package skeleton.seekbar.entity.draggables

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import skeleton.seekbar.IDragable

open class DraggableBitmap(
    val bitmap: Bitmap,
    viewTag: String,
    percent: Float,
    sticky: Boolean = false,
    iSeekBarChangeListener: ((IDragable, SLIDER_GESTURE) -> Unit)  = { _, _ -> }
) : AbstractDraggable(viewTag, percent, sticky, iSeekBarChangeListener) {

    val bitmapW = bitmap.width
    val bitmapH = bitmap.height

    private var drawMechanism: (Canvas) -> Unit = { canvas ->
        canvas.drawBitmap(bitmap, null, rect, null)
    }

    init {
        if (bitmapW != bitmapH) {
            val tempRect = RectF()
            drawMechanism = { canvas ->
                val centerX = rect.centerX()
                val centerY = rect.centerY()
                tempRect.set(
                    centerX - bitmapW / 2f,
                    centerY - bitmapH / 2f,
                    centerX + bitmapW / 2f,
                    centerY + bitmapH / 2f
                )
                canvas.drawBitmap(bitmap, null, tempRect, null)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        drawMechanism(canvas)
    }
}
