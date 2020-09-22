package com.sduduzog.slimlauncher

import android.graphics.Color
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import javax.inject.Inject

class ThemeBindingAdapter @Inject constructor() {

    @BindingAdapter(value = ["themeTextColor"])
    fun setTextViewColor(textView: TextView, color: String) {
        textView.setTextColor(Color.parseColor(color))
    }

    @BindingAdapter(value = ["themeBackgroundColor"])
    fun setViewBackgroundColor(constraintLayout: ConstraintLayout, color: String) {
        constraintLayout.setBackgroundColor(Color.parseColor(color))
    }
}