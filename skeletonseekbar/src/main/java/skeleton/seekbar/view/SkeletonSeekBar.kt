package mitsuru.msb.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import skeleton.seekbar.*
import skeleton.seekbar.entity.SkeletonSeekBarAttrs
import skeleton.seekbar.entity.draggables.SLIDER_GESTURE
import java.util.*

/**
 * Created by ivanm on 3/11/2018.
 */

class SkeletonSeekBar(context: Context, attrs: AttributeSet) : View(context, attrs),
    View.OnTouchListener {

    val attributes: SkeletonSeekBarAttrs = SkeletonSeekBarAttrs(context, attrs)
    private val itemsList = ArrayList<IDragable>()

    private var focusedDraggable: IDragable? = null

    private var margin: Float = 0f
    var lineY: Float = 0f

    init {
        setOnTouchListener(this)
        minimumHeight = 30.DpToPx()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!attributes.respectMarginToDrawInsideContainer) this.setAllParentsClip(false)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        lineY = h * attributes.gravityY
        margin = attributes.itemW * 0.4f
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //unactive line drawing
        canvas.drawLine(
            0f,
            lineY,
            width.toFloat(),
            lineY, attributes.colorLineBg
        )

        //active line drawing
        when (itemsList.size) {
            0 -> {                //do nothing
            }
            1 -> {
                val coordinate = valueToCoordinate(itemsList[0].getDraggableValueWrapper().value)
                val x1 = if (attributes.respectMarginToDrawInsideContainer) margin else 0f
                canvas.drawLine(
                    x1,
                    lineY,
                    coordinate,
                    lineY, attributes.colorLineActive
                )
            }
            else -> {
                itemsList.forEachIndexed { index, iDragable ->
                    if (index + 1 < itemsList.size) {
                        val coordinateStart =
                            valueToCoordinate(iDragable.getDraggableValueWrapper().value)
                        val coordinateEnd =
                            valueToCoordinate(itemsList[index + 1].getDraggableValueWrapper().value)
                        canvas.drawLine(
                            coordinateStart,
                            lineY,
                            coordinateEnd,
                            lineY, attributes.colorLineActive
                        )
                    }
                }
            }
        }

        itemsList.forEach {
            it.onUpdate(attributes.itemW, attributes.itemH)
            it.onDraw(canvas)
        }
    }

    fun valueToCoordinate(value: Float): Float {
        val valuePercent =
            (value - attributes.min) * 100f / (attributes.max - attributes.min) / 100f
        return margin + (width - margin * 2) * valuePercent
    }

    private fun coordinateToValue(coordinate: Float): Float {
        val minValue = attributes.min
        val maxValue = attributes.max

        val valuePercent = (coordinate - margin) / (width - margin * 2)
        val value = minValue + (maxValue - minValue) * valuePercent
        if (value < minValue) {
            return minValue
        } else if (value > maxValue) {
            return maxValue
        }
        return value
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val viewParent = v.parent
        viewParent?.requestDisallowInterceptTouchEvent(true)
        when {
            event.action == MotionEvent.ACTION_DOWN -> {
                //find what value we are changing and focus on it
                focusedDraggable = itemsList.findClosestTo(coordinateToValue(event.x)) {
                    it.getDraggableValueWrapper().value
                }
                val focusedDraggable = focusedDraggable ?: return true
                //change focused value
                moveItemTo(focusedDraggable, coordinateToValue(event.x))
                invalidate()
                focusedDraggable.iSeekBarChangeListener(focusedDraggable, SLIDER_GESTURE.FOCUSED)
            }
            focusedDraggable != null && event.action == MotionEvent.ACTION_MOVE -> {
                val focusedDraggable = focusedDraggable ?: return true
                moveItemTo(focusedDraggable, coordinateToValue(event.x))
                invalidate()
                focusedDraggable.iSeekBarChangeListener(focusedDraggable, SLIDER_GESTURE.MOVED)
            }
            focusedDraggable != null && event.action == MotionEvent.ACTION_UP -> {
                val focusedDraggable = focusedDraggable ?: return true
                if (focusedDraggable.isSticky) {
                    val currentValue = focusedDraggable.getDraggableValueWrapper().value
                    val step = attributes.step

                    val lost = currentValue % step
                    moveItemTo(
                        focusedDraggable,
                        if (lost < step / 2f) {
                            currentValue - lost
                        } else {
                            currentValue - lost + step
                        }
                    )
                    invalidate()
                }
                focusedDraggable.iSeekBarChangeListener(focusedDraggable, SLIDER_GESTURE.RELEASED)
                this.focusedDraggable = null
                return false
            }
        }
        return true
    }

    open fun moveItemTo(draggable: IDragable, nValue: Float) {
        val index = itemsList.indexOf(draggable)
        if (draggable.getDraggableValueWrapper().value > nValue) {
            if (index == 0) {
                draggable.getDraggableValueWrapper().value = nValue
                return
            }
            val prevValue = itemsList.get(index - 1).getDraggableValueWrapper().value
            if (prevValue + attributes.draggableDifference < nValue) {
                draggable.getDraggableValueWrapper().value = nValue
            } else {
                draggable.getDraggableValueWrapper().value =
                    prevValue + attributes.draggableDifference
            }
        } else
            if (draggable.getDraggableValueWrapper().value < nValue) {
                if (index == itemsList.size - 1) {
                    draggable.getDraggableValueWrapper().value = nValue
                    return
                }
                val nextValue = itemsList.get(index + 1).getDraggableValueWrapper().value
                if (nextValue - attributes.draggableDifference > nValue) {
                    draggable.getDraggableValueWrapper().value = nValue
                } else {
                    draggable.getDraggableValueWrapper().value =
                        nextValue - attributes.draggableDifference
                }
            }
    }

    open fun addSlider(iDragable: IDragable) {
        itemsList.add(iDragable)
        iDragable.attachTo(this)
        invalidate()
    }

    open fun setSliderValue(tag: String, value: Float) {
        val slider = itemsList.find { it.getTag() == tag }
        slider?.getDraggableValueWrapper()!!.value = value
        invalidate()
    }

    open fun getSliderValue(tag: String): Float {
        val slider = itemsList.find { it.getTag() == tag }
        return if (slider == null) {
            -1f
        } else {
            slider.getDraggableValueWrapper().value
        }
    }
}
