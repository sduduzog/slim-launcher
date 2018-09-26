package com.sduduzog.slimlauncher

import android.arch.lifecycle.ViewModelProviders
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener, NavController.OnNavigatedListener {

    private lateinit var settings: SharedPreferences
    private val label = "main_fragment"
    private lateinit var currentLabel: String
    var fragmentBackPressed: IOnBackPressed? = null
    private lateinit var viewModel: MainViewModel
    private val TAG: String = "MainActivity"

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

        val active = settings.getInt("theme", 0)
        when (active) {
            0 -> {
                theme.applyStyle(R.style.AppTheme, true)
            }
            1 -> {
                theme.applyStyle(R.style.AppDarkTheme, true)
            }
            2 -> {
                theme.applyStyle(R.style.AppGreyTheme, true)
            }
            3 -> {
                theme.applyStyle(R.style.AppTealTheme, true)
            }
            4 -> {
                theme.applyStyle(R.style.AppPinkTheme, true)
            }
        }
        return theme
    }

    override fun onBackPressed() {
        if (fragmentBackPressed != null) {
            if (fragmentBackPressed?.onBackPressed() as Boolean) {
                return
            }
        }
        if (currentLabel != label)
            super.onBackPressed()
    }

    override fun onNavigated(controller: NavController, destination: NavDestination) {
        currentLabel = destination.label.toString()
    }

    interface IOnBackPressed {
        fun onBackPressed(): Boolean
    }

}
