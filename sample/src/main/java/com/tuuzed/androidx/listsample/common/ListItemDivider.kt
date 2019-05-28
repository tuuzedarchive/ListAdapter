package com.tuuzed.androidx.listsample.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ListItemDivider(context: Context) : RecyclerView.ItemDecoration() {
    private val height = 1 //px
    private val divider: Drawable?

    init {
        val a = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        divider = a.getDrawable(0)
        a.recycle()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = height // px
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val left = child.left + 8
            val right = child.right - 8
            val top = child.bottom
            val bottom = top + height
            divider?.setBounds(left, top, right, bottom)
            divider?.draw(c)

        }
    }
}