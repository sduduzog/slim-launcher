package com.sduduzog.slimlauncher

import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import com.sduduzog.slimlauncher.ui.main.MainViewModel


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener, NavController.OnDestinationChangedListener {

    // TODO: Clickable apps while in preferences, intuitiveness
    // TODO: Lock screen on double tap
    // TODO: Move some apps to bottom sheet.

    private lateinit var settings: SharedPreferences
    private val label = "main_fragment"
    private lateinit var currentLabel: String
    private lateinit var viewModel: MainViewModel
    private lateinit var navigator: NavController
    var onBackPressedListener: OnBackPressedListener? = null


    var dispatcher: Subject = object : Subject() {

        var observers: MutableSet<Observer> = mutableSetOf()
        override fun attachObserver(o: Observer) {
            observers.add(o)
        }

        override fun detachObserver(o: Observer) {
            observers.remove(o)
        }

        override fun notifyObservers() {
            for (o in observers) {
                o.update("onBackPressed")
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        settings = getSharedPreferences(getString(R.string.prefs_settings), MODE_PRIVATE)
        settings.registerOnSharedPreferenceChangeListener(this)
        navigator = findNavController(this, R.id.nav_host_fragment)
        navigator.addOnDestinationChangedListener(this)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        viewModel.refreshApps()
    }

    override fun onResume() {
        super.onResume()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        toggleStatusBar()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) toggleStatusBar()
    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, s: String?) {
        if (s.equals(getString(R.string.prefs_settings_key_theme), true)) {
            recreate()
        }
        if (s.equals(getString(R.string.prefs_settings_key_hide_status_bar), true)) {
            toggleStatusBar()
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
        dispatcher.notifyObservers()
        if (currentLabel != label)
            super.onBackPressed()
        else onBackPressedListener?.onBackPress()
    }

    /**
     * Make the activity aware of the current destination in our NavController.
     */
    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        currentLabel = destination.label.toString()
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PHONE_CALL -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    navigator.navigate(R.id.action_mainFragment_to_dialerFragment)
                } else {
                    // Do nothing
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun toggleStatusBar() {
        val isHidden = settings.getBoolean(getString(R.string.prefs_settings_key_hide_status_bar), false)
        if (isHidden) {
            hideSystemUI()
        } else {
            showSystemUI()
        }
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
                    return R.style.AppCandyTheme
                }
                5 -> {
                    return R.style.AppPinkTheme
                }
            }
            return R.style.AppTheme
        }

        const val REQUEST_PHONE_CALL = 1
    }

    interface OnBackPressedListener {
        fun onBackPress()
        fun onBackPressed()
    }
}
