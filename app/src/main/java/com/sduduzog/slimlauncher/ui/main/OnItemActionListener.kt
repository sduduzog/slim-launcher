package com.sduduzog.slimlauncher.ui.main

interface OnItemActionListener {
    fun onViewMoved(oldPosition: Int, newPosition: Int): Boolean
    fun onViewSwiped(position: Int)
    fun onViewIdle()
}