package mitsura.skeleton.seekbar.entity.draggables

import android.graphics.Bitmap
import android.graphics.Canvas
import mitsura.skeleton.seekbar.entity.draggables.AbstractDraggable

open class DraggableBitmap(val bitmap: Bitmap, viewTag: String, percent: Float) : AbstractDraggable(viewTag, percent) {

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, null, rect, null)
    }
}