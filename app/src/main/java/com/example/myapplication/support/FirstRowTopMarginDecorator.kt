package com.example.myapplication.support

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FirstRowTopMarginDecorator(private val marginTop: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val layoutManager = parent.layoutManager as? GridLayoutManager

        if (layoutManager != null && layoutManager.spanCount > 0 && position < layoutManager.spanCount) {
            outRect.top = marginTop
        } // Check if it's the first row
    }
}
