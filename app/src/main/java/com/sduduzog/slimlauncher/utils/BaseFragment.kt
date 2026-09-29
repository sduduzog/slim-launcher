package com.sduduzog.slimlauncher.utils

import android.content.Intent
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.R as AppCompatR
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.sduduzog.slimlauncher.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment(), ISubscriber {

    abstract fun getFragmentView(): ViewGroup


    override fun onResume() {
        super.onResume()
        val settings = requireContext().getSharedPreferences(getString(R.string.prefs_settings), AppCompatActivity.MODE_PRIVATE)
        val active = settings.getInt(getString(R.string.prefs_settings_key_theme), 0)

        val window = requireActivity().window
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = active in setOf(0, 3, 5)

        if (Build.VERSION.SDK_INT in Build.VERSION_CODES.M until Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            val value = TypedValue()
            requireContext().theme.resolveAttribute(AppCompatR.attr.colorPrimary, value, true)
            setLegacyStatusBarColor(value.data)
        }

    }

    @Suppress("DEPRECATION")
    private fun setLegacyStatusBarColor(color: Int) {
        requireActivity().window.statusBarColor = color
    }

    override fun onStart() {
        super.onStart()
        with(activity as IPublisher) {
            this.attachSubscriber(this@BaseFragment)
        }
    }

    override fun onStop() {
        super.onStop()
        with(activity as IPublisher) {
            this.detachSubscriber(this@BaseFragment)
        }
    }

    protected fun launchActivity(view: View, intent: Intent) {
        val left = 0
        val top = 0
        val width = view.measuredWidth
        val height = view.measuredHeight
        val opts = ActivityOptionsCompat.makeClipRevealAnimation(view, left, top, width, height)
        startActivity(intent, opts.toBundle())
    }

    open fun onBack(): Boolean = false

    open fun onHome() {}
}
