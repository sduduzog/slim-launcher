package com.sduduzog.slimlauncher.ui.main

import android.view.View

abstract class DoubleClickListener : View.OnClickListener { // courtesy of Xar E Ahmer @ stackoverflow

    private var lastTickTime: Long = 0

    override fun onClick(view: View) {
        val clickTime = System.currentTimeMillis()
        if (clickTime - lastTickTime < DOUBLE_CLICK_TIME_DELTA) {
            onDoubleClick(view)
        } else {
            onSingleClick(view)
        }
        lastTickTime = clickTime
    }

    abstract fun onDoubleClick(v: View)
    abstract fun onSingleClick(v: View)

    companion object {
        const val DOUBLE_CLICK_TIME_DELTA = 300 // milliseconds
    }
}