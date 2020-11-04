package com.sergey.myapplication

import android.graphics.Canvas
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener


class TestDecoration(
        recyclerView: RecyclerView,
        private val listener: TestHeaderInterface
) : ItemDecoration() {
    private var mStickyHeaderWidth = 0

    interface TestHeaderInterface {
        fun bindHeaderData(header: View, position: Int)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val topChild = parent.getChildAt(0)
        for(index in 0..4) {
            val child = parent.getChildAt(index)
            val currentHeader: View = getHeaderViewForItem(index, parent)
            fixLayoutSize(parent, currentHeader)
            val textView = child.findViewById(R.id.testTextView) as TextView
            c.save()
            c.translate(child.left.toFloat() + - topChild.left, child.top.toFloat())
            currentHeader.draw(c)
            c.restore()
        }
    }

    private fun getHeaderViewForItem(itemPosition: Int, parent: RecyclerView): View {
        val layoutResId = R.layout.item_text
        val header: View = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        listener.bindHeaderData(header, itemPosition)
        return header
    }

    /**
     * Properly measures and layouts the top sticky header.
     * @param parent ViewGroup: RecyclerView in this case.
     */
    private fun fixLayoutSize(parent: ViewGroup, view: View) {

        // Specs for parent (RecyclerView)
        val widthSpec: Int = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec: Int = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.EXACTLY)

        // Specs for children (headers)
        val childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft + parent.paddingRight, view.getLayoutParams().width)
        val childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, parent.paddingTop + parent.paddingBottom, view.getLayoutParams().height)
        view.measure(childWidthSpec, childHeightSpec)
        view.layout(0, 0, view.measuredWidth.also { mStickyHeaderWidth = it }, view.measuredHeight)
    }

    init {

        // On Sticky Header Click
        recyclerView.addOnItemTouchListener(object : OnItemTouchListener {
            override fun onInterceptTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent): Boolean {
                Log.d("TEST", "mStickyHeaderWidth: $mStickyHeaderWidth")
                return if (motionEvent.x <= mStickyHeaderWidth) {
                    Log.d("TEST", "Click!")
                    // Handle the clicks on the header here ...
                    true
                } else false
            }

            override fun onTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
    }
}