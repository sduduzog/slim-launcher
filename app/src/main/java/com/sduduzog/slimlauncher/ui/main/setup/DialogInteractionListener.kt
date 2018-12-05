package com.sduduzog.slimlauncher.ui.main.setup

import com.sduduzog.slimlauncher.data.App


interface DialogInteractionListener {
    fun onAppsChosen(apps: List<App>)
    fun onClockChosen()
}