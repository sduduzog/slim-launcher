package com.sduduzog.slimlauncher

import com.sduduzog.slimlauncher.ui.main.model.App

internal object TestUtil {

    fun createApp(appName: String, packageName: String, activityName: String): App {
        return App(appName, packageName, activityName)
    }
}
