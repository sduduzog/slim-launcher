package com.sduduzog.slimlauncher.ui.options

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.edit
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.utils.BaseFragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.options_elements_fragment.*


class OptionsElementsFragment : BaseFragment(){
    private lateinit var settings:SharedPreferences

    override fun getFragmentView(): ViewGroup = options_elements_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.options_elements_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        settings = context!!.getSharedPreferences(getString(R.string.prefs_settings), Context.MODE_PRIVATE)

        // Set current state values for each view
        options_fragment_toggle_status_bar_state.setText(currentState(R.string.prefs_settings_key_toggle_status_bar))
        options_fragment_toggle_time_state.setText(currentState(R.string.prefs_settings_key_toggle_time))
        options_fragment_toggle_date_state.setText(currentState(R.string.prefs_settings_key_toggle_date))
        options_fragment_toggle_call_state.setText(currentState(R.string.prefs_settings_key_toggle_call))
        options_fragment_toggle_camera_state.setText(currentState(R.string.prefs_settings_key_toggle_camera))
        options_fragment_time_as_shortcut_state.setText(currentState(R.string.prefs_settings_key_shortcut_time))
        options_fragment_date_as_shortcut_state.setText(currentState(R.string.prefs_settings_key_shortcut_date))

        toggleConditionalOptions()

        // Set listeners on each option
        addListener(options_fragment_toggle_status_bar, options_fragment_toggle_status_bar_state, R.string.prefs_settings_key_toggle_status_bar)
        addListener(options_fragment_toggle_time, options_fragment_toggle_time_state, R.string.prefs_settings_key_toggle_time)
        addListener(options_fragment_toggle_date, options_fragment_toggle_date_state, R.string.prefs_settings_key_toggle_date)
        addListener(options_fragment_toggle_call, options_fragment_toggle_call_state, R.string.prefs_settings_key_toggle_call)
        addListener(options_fragment_toggle_camera, options_fragment_toggle_camera_state, R.string.prefs_settings_key_toggle_camera)
        addListener(options_fragment_time_as_shortcut, options_fragment_time_as_shortcut_state, R.string.prefs_settings_key_shortcut_time)
        addListener(options_fragment_date_as_shortcut, options_fragment_date_as_shortcut_state, R.string.prefs_settings_key_shortcut_date)

    }

    fun currentState(settingRef : Int) : Int{
        val bool = settings.getBoolean(getString(settingRef), true)

        return when(isOnOffElement(settingRef)){
            true -> if(bool) R.string.options_elements_on else R.string.options_elements_off
            false -> if(bool) R.string.options_elements_shown else R.string.options_elements_hidden
        }
    }

    fun isOnOffElement (settingRef : Int) : Boolean {
        return settingRef == R.string.prefs_settings_key_shortcut_time || settingRef == R.string.prefs_settings_key_shortcut_date
    }

    fun addListener(view : TextView, stateView : TextView, settingRef : Int){
        view.setOnClickListener {
            val pref = getString(settingRef)
            val bool = settings.getBoolean(pref, true)
            settings.edit {
                putBoolean(pref, !bool)
            }
            stateView.setText(currentState(settingRef))
            toggleConditionalOptions()
        }
    }

    fun toggleConditionalOptions(){
            var enabled = settings.getBoolean(getString(R.string.prefs_settings_key_toggle_time), true)
            options_fragment_time_as_shortcut.isEnabled = enabled
            options_fragment_time_as_shortcut_state.isEnabled = enabled

            enabled = settings.getBoolean(getString(R.string.prefs_settings_key_toggle_date), true)
            options_fragment_date_as_shortcut.isEnabled = enabled
            options_fragment_date_as_shortcut_state.isEnabled = enabled
    }
}