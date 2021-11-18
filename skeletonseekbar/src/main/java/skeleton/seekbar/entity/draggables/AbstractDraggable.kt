package skeleton.seekbar.entity.draggables

import android.graphics.Rect
import mitsuru.msb.view.SkeletonSeekBar
import skeleton.seekbar.IDragable
import skeleton.seekbar.entity.ValueWrapper
import java.lang.RuntimeException

enum class SLIDER_GESTURE { FOCUSED, MOVED, RELEASED }

abstract class AbstractDraggable(
        private val mTag: String,
        private val initPosPercent: Float,
        override val isSticky: Boolean,
        override var iSeekBarChangeListener: ((IDragable, SLIDER_GESTURE) -> Unit)
) : IDragable {

    init {
        if (initPosPercent > 1) {
            throw RuntimeException("Initial percent bigger that 1, which is considered 100% by app, DENIED")
        }
    }

    val value = ValueWrapper()
    protected lateinit var seekbar: SkeletonSeekBar
    protected val rect: Rect = Rect()

    override fun attachTo(mitsuraSeekBar: SkeletonSeekBar) {
        seekbar = mitsuraSeekBar
        value.value = (seekbar.attributes.max - seekbar.attributes.min) * initPosPercent + seekbar.attributes.min
    }

    override fun onUpdate(itemWidth: Int, itemHeight: Int) {
        val coordinate = seekbar.valueToCoordinate(value.value)
        rect.set(
                (coordinate - itemWidth / 2).toInt(), (seekbar.lineY + itemHeight / 2).toInt(),
                (coordinate + itemWidth / 2).toInt(), (seekbar.lineY - itemHeight / 2).toInt()
        )
    }

    override fun getTag(): String = mTag

    override fun getDraggableValueWrapper(): ValueWrapper = value
}
