package com.sduduzog.slimlauncher.data.model

import com.sduduzog.slimlauncher.data.entity.HomeApp


data class App(
        val appName: String,
        val packageName: String,
        val activityName: String
) {
    companion object {
        fun from(homeApp: HomeApp): App = App(homeApp.appName, homeApp.packageName, homeApp.activityName)
    }
}