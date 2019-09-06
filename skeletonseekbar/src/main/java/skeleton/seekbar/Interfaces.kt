package skeleton.seekbar

import android.graphics.Canvas
import android.view.MotionEvent
import mitsuru.msb.view.SkeletonSeekBar
import skeleton.seekbar.entity.ValueWrapper

interface ISeekBarGestureListener {
    fun onSeekbarItemFocused(tag: String, value: Float)
    fun onSeekbarItemMoved(tag: String, value: Float)
    fun onSeekbarItemReleased(tag: String, value: Float)
}

interface ISeekBarChangeListener {
    fun onSeekBarValueChange(tag: String, value: Float)
}

interface ITaggable {
    fun getTag(): String
}

interface IDragable : ITaggable {
    fun onUpdate(width: Int, height: Int)
    fun onDraw(canvas: Canvas)
    fun attachTo(mitsuraSeekBar: SkeletonSeekBar)
    fun getDraggableValueWrapper(): ValueWrapper
    fun onMotionEvent(event: MotionEvent)
}
