package com.sduduzog.slimlauncher

import android.app.ActivityManager
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

private lateinit var settings: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        settings = getSharedPreferences("settings", MODE_PRIVATE)
        settings.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(this, R.id.nav_host_fragment).navigateUp()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, s: String?) {
        recreate()
    }

//    override fun onPause() {
//        super.onPause()
//
//        val activityManager = applicationContext
//                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//
//        activityManager.moveTaskToFront(taskId, 0)
//    }

    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()
        settings = getSharedPreferences("settings", MODE_PRIVATE)

        val active = settings.getBoolean("theme", false)
        if (active) {
            theme.applyStyle(R.style.AppDarkTheme, true)
        } else {
            theme.applyStyle(R.style.AppLightTheme, true)
        }
        return theme
    }

    override fun onBackPressed() {}
}
