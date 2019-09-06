package skeleton.seekbar

import android.graphics.Canvas
import android.view.MotionEvent
import mitsuru.msb.view.SkeletonSeekBar
import skeleton.seekbar.entity.ValueWrapper
import skeleton.seekbar.entity.draggables.AbstractDraggable
import skeleton.seekbar.entity.draggables.SLIDER_GESTURE

interface ITaggable {
    fun getTag(): String
}

interface IDragable : ITaggable {

    val isSticky: Boolean
    var iSeekBarChangeListener: ((IDragable, SLIDER_GESTURE) -> Unit)

    fun onUpdate(width: Int, height: Int)
    fun onDraw(canvas: Canvas)
    fun attachTo(mitsuraSeekBar: SkeletonSeekBar)
    fun getDraggableValueWrapper(): ValueWrapper
}
