package mitsura.skeleton.seekbar

import android.graphics.Canvas
import mitsura.skeleton.seekbar.entity.ValueWrapper
import mitsuru.msb.view.MitsuraSeekBar

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
    fun attachTo(mitsuraSeekBar: MitsuraSeekBar)
    fun getDraggableValueWrapper(): ValueWrapper
}
