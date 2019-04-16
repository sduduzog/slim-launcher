package com.sduduzog.slimlauncher.utils

import android.view.View
import com.sduduzog.slimlauncher.data.HomeApp

interface OnLaunchAppListener{
    fun onLaunch(app: HomeApp, view: View)
}