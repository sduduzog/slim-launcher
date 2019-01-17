package com.sduduzog.slimlauncher

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import com.sduduzog.slimlauncher.ui.main.MainViewModel


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener, NavController.OnDestinationChangedListener {

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_ENABLE_ADMIN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(applicationContext, "Registered As Admin", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Failed to register as Admin", Toast.LENGTH_SHORT).show()
            }
        }
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

        const val REQUEST_CODE_ENABLE_ADMIN = 1
    }

    interface OnBackPressedListener {
        fun onBackPress()
        fun onBackPressed()
    }
}
