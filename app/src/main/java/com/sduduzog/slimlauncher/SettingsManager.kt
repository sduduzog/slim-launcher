package com.sduduzog.slimlauncher

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsManager @Inject constructor(val sharedPref: SharedPreferences) {}