package com.sduduzog.slimlauncher.ui.main.setup

import com.sduduzog.slimlauncher.ui.main.model.App

interface DialogInteractionListener {
    fun onAppsChosen(apps: List<App>)
    fun onClockChosen()
}