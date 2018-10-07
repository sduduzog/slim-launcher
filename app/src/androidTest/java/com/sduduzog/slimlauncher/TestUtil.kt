package com.sduduzog.slimlauncher

import com.sduduzog.slimlauncher.data.App

internal object TestUtil {

    fun createApp(appName: String, packageName: String, activityName: String): App {
        val app = App()
        app.appName = appName
        app.packageName = packageName
        app.activityName = activityName
        return app
    }
}
