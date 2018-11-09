package mitsura.skeleton.seekbar.entity.draggables

import android.graphics.Rect
import mitsura.skeleton.seekbar.IDragable
import mitsura.skeleton.seekbar.entity.ValueWrapper
import mitsuru.msb.view.MitsuraSeekBar


abstract class AbstractDraggable(private val mTag: String, private val initPosPercent: Float) : IDragable {

    val value = ValueWrapper()
    protected lateinit var seekbar: MitsuraSeekBar
    protected val rect: Rect = Rect()

    override fun attachTo(mitsuraSeekBar: MitsuraSeekBar) {
        seekbar = mitsuraSeekBar
        value.value = (seekbar.attributes.max - seekbar.attributes.min) * initPosPercent + seekbar.attributes.min
    }

    override fun onUpdate(itemWidth: Int, itemHeight: Int) {
        val coordinate = seekbar.valueToCoordinate(value.value)
        rect.set((coordinate - itemWidth / 2).toInt(), (seekbar.lineY + itemHeight / 2).toInt(),
                (coordinate + itemWidth / 2).toInt(), (seekbar.lineY - itemHeight / 2).toInt())
    }

    override fun getTag(): String = mTag

    override fun getDraggableValueWrapper(): ValueWrapper = value
}
