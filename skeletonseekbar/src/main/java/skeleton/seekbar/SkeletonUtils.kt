package skeleton.seekbar

import android.content.res.Resources
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup

fun Paint.getRectForTextWith(text: String, width: Float, height: Float, shrinkCoef: Float = 0.8f): Rect {
    val rect = Rect()
    do {
        getTextBounds(text, 0, text.length, rect)
        textSize -= 1
    } while (rect.width() > width * shrinkCoef || rect.height() > height * shrinkCoef)
    return rect
}

fun Paint.getTextBounds(text: String, rect: Rect) = getTextBounds(text, 0, text.length, rect)

fun Number.DpToPx() = (this.toInt() * Resources.getSystem().displayMetrics.density).toInt()

fun <T> List<T>.findClosestTo(value: Float, predicate: (T) -> Float): T? {
    if (size == 1) return this[0]

    fun checkTwo(lItem: T, rItem: T): T? {
        val left = predicate(lItem)
        val right = predicate(rItem)
        if (left < value && value < right) {
            val distanceToLeft = Math.abs(value - left)
            val distanceToRight = Math.abs(value - right)
            if (distanceToLeft < distanceToRight) {
                return lItem
            } else {
                return rItem
            }
        }
        return null
    }
    for (i in 0 until size) {
        when (i) {
            0 -> {
                if (predicate(this[i]) > value) {
                    return this[i]
                } else {
                    val item = checkTwo(this[i], this[i + 1])
                    if (item != null) return item
                }
            }
            size - 1 -> {
                if (predicate(this[i]) < value) return this[i]
            }
            else -> {
                val item = checkTwo(this[i], this[i + 1])
                if (item != null) return item
            }
        }
    }
    return null
}

fun View.setAllParentsClip(enabled: Boolean) {
    var parent = parent
    while (parent is ViewGroup) {
        parent.clipChildren = enabled
        parent.clipToPadding = enabled
        parent = parent.parent
    }
}