package com.sduduzog.slimlauncher

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import javax.inject.Inject

class ThemeBindingAdapter @Inject constructor() {

    @BindingAdapter(value = ["themeTextColor"])
    fun setTextViewColor(textView: TextView, color: String) {
        textView.setTextColor(Color.parseColor(color))
    }
}