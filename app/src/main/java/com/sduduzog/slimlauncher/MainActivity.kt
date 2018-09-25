package com.sduduzog.slimlauncher

import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import com.sduduzog.slimlauncher.ui.main.MainViewModel


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener, NavController.OnNavigatedListener {

    private lateinit var settings: SharedPreferences
    private val label = "main_fragment"
    private lateinit var currentLabel: String

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        settings = getSharedPreferences("settings", MODE_PRIVATE)
        settings.registerOnSharedPreferenceChangeListener(this)
        val navigator = findNavController(this, R.id.nav_host_fragment)
        navigator.addOnNavigatedListener(this)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateApps()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, s: String?) {
        recreate()
    }

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

    override fun onBackPressed() {
        if (currentLabel != label)
            super.onBackPressed()
    }

    override fun onNavigated(controller: NavController, destination: NavDestination) {
        currentLabel = destination.label.toString()
    }
}
