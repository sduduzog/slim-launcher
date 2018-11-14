package com.sduduzog.slimlauncher.ui.main.settings

interface OnItemActionListener {
    fun onViewMoved(oldPosition: Int, newPosition: Int): Boolean
    fun onViewSwiped(position: Int)
    fun onViewIdle()
}