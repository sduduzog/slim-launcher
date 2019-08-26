package com.sduduzog.slimlauncher.ui

import android.content.*
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.utils.BaseFragment
import com.sduduzog.slimlauncher.utils.HomeWatcher
import dagger.android.AndroidInjection
import javax.inject.Inject


class MainActivity : AppCompatActivity(),
        SharedPreferences.OnSharedPreferenceChangeListener, HomeWatcher.OnHomePressedListener {


    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var settings: SharedPreferences
    private lateinit var navigator: NavController
    private lateinit var homeWatcher: HomeWatcher
    internal lateinit var viewModel: MainViewModel
    private lateinit var receiver: ClockReceiver
    private val subscribers: MutableSet<BaseFragment> = mutableSetOf()

    fun attachSubscriber(s: BaseFragment) {
        subscribers.add(s)
    }

    fun detachSubscriber(s: BaseFragment) {
        subscribers.remove(s)
    }

    private fun dispatchBack() {
        for (s in subscribers) if (s.onBack()) return
        completeBackAction()
    }

    private fun dispatchHome() {
        for (s in subscribers) s.onHome()
    }

    internal fun dispatchTick() {
        subscribers.forEach { it.onTick() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        setContentView(R.layout.main_activity)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        settings = getSharedPreferences(getString(R.string.prefs_settings), MODE_PRIVATE)
        settings.registerOnSharedPreferenceChangeListener(this)
        navigator = findNavController(this, R.id.nav_host_fragment)
        homeWatcher = HomeWatcher(this)
        homeWatcher.setOnHomePressedListener(this)
        receiver = ClockReceiver()
    }

    override fun onResume() {
        super.onResume()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        toggleStatusBar()
    }

    override fun onStart() {
        super.onStart()
        homeWatcher.startWatch()
        registerReceiver(receiver, IntentFilter(Intent.ACTION_TIME_TICK))
    }

    override fun onStop() {
        super.onStop()
        homeWatcher.stopWatch()
        unregisterReceiver(receiver)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) toggleStatusBar()
    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, s: String?) {
        if (s.equals(getString(R.string.prefs_settings_key_theme), true)) {
            recreate()
        }
        if (s.equals(getString(R.string.prefs_settings_key_toggle_status_bar), true)) {
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
        dispatchBack()
    }

    override fun onHomePressed() {
        dispatchHome()
        navigator.popBackStack(R.id.homeFragment, false)
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun toggleStatusBar() {
        val isHidden = settings.getBoolean(getString(R.string.prefs_settings_key_toggle_status_bar), false)
        if (isHidden) {
            hideSystemUI()
        } else {
            showSystemUI()
        }
    }

    companion object {

        fun resolveTheme(i: Int): Int {
            return when (i) {
                1 -> R.style.AppDarkTheme
                2 -> R.style.AppGreyTheme
                3 -> R.style.AppTealTheme
                4 -> R.style.AppCandyTheme
                5 -> R.style.AppPinkTheme
                else -> R.style.AppTheme
            }
        }
    }

    private fun completeBackAction() {
        super.onBackPressed()
    }

    inner class ClockReceiver : BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent?) {
            viewModel.tick()
            dispatchTick()
        }
    }
}
