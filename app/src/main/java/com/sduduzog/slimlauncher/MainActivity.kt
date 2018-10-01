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
    private lateinit var viewModel: MainViewModel
    private lateinit var navigator: NavController
    var onBackPressedListener: OnBackPressedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        settings = getSharedPreferences(getString(R.string.prefs_settings), MODE_PRIVATE)
        settings.registerOnSharedPreferenceChangeListener(this)
        navigator = findNavController(this, R.id.nav_host_fragment)
        navigator.addOnNavigatedListener(this)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateApps()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, s: String?) {
        if (s.equals(getString(R.string.prefs_settings_key_theme), true)) {
            recreate()
        }
    }

    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()
        settings = getSharedPreferences(getString(R.string.prefs_settings), MODE_PRIVATE)
        val active = settings.getInt(getString(R.string.prefs_settings_key_theme), 0)
        theme.applyStyle(resolveTheme(active), true)
        return theme
    }

    override fun onBackPressed() {
        if (currentLabel != label)
            super.onBackPressed()
        else onBackPressedListener?.onBackPressed()
    }

    override fun onNavigated(controller: NavController, destination: NavDestination) {
        currentLabel = destination.label.toString()
    }

    companion object {
        fun resolveTheme(i: Int): Int {
            when (i) {
                1 -> {
                    return R.style.AppDarkTheme
                }
                2 -> {
                    return R.style.AppGreyTheme
                }
                3 -> {
                    return R.style.AppTealTheme
                }
                4 -> {
                    return R.style.AppPinkTheme
                }
            }
            return R.style.AppTheme
        }
    }
    interface OnBackPressedListener{
        fun onBackPressed()
    }
}
