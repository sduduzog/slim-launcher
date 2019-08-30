package com.sduduzog.slimlauncher

interface AppConstants {
    companion object {

        const val TIME_FORMAT_KEY: String = "time_format"
        const val HIDE_STATUS_BAR: String = "hide_bar"

        const val DEFAULT_TIME_FORMAT: String = "h:mm aa"
        const val SECONDARY_TIME_FORMAT: String = "H:mm"

        enum class THEMES {
            Default,
            Midnight,
            Jupiter,
            Teal,
            Candy,
            Pastel
        }
    }
}