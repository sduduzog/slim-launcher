package com.sduduzog.slimlauncher.ui.main

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sduduzog.slimlauncher.R

abstract class StatusBarThemeFragment : Fragment(){

    private fun setLightStatusBar(window: Window, view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val flags = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
            window.statusBarColor = Color.WHITE
        }
    }

    private fun clearLightStatusBar(window: Window, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val value = TypedValue()
            context.theme.resolveAttribute(R.attr.colorPrimary, value, true)
            window.statusBarColor = value.data
        }
    }


    /**
     * @return [android.view.View] of the [androidx.fragment.app.Fragment] extending this class to apply flags to
     */
    abstract fun getFragmentView() : View

    override fun onResume() {
        super.onResume()
        val settings = context!!.getSharedPreferences(getString(R.string.prefs_settings), AppCompatActivity.MODE_PRIVATE)
        val active = settings.getInt(getString(R.string.prefs_settings_key_theme), 0)
        if (active == 0) {
            setLightStatusBar(activity!!.window, getFragmentView())
        } else {
            clearLightStatusBar(activity!!.window, context!!)
        }
    }
}