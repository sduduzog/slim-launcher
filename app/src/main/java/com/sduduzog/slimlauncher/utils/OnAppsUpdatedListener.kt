package com.sduduzog.slimlauncher.utils

import com.sduduzog.slimlauncher.data.HomeApp

interface OnAppsUpdatedListener {
    fun onAppsUpdated(list: List<HomeApp>)
}