package com.sduduzog.slimlauncher.ui.options

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.navigation.Navigation
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.sduduzog.slimlauncher.R

class OptionsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.options_fragment, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        val key = preference?.key

        if (getString(R.string.prefs_settings_key_open_customize_apps).equals(key)){
            val navigator = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navigator.navigate(R.id.action_optionsFragment_to_customiseAppsFragment)
            return true
        }

        if(getString(R.string.prefs_settings_key_open_device_settings).equals(key)){
            val intent = Intent(Settings.ACTION_SETTINGS)
            startActivity(intent)
            return true
        }

        if(key.equals("load_options_elements")){
            val navigator = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navigator.navigate(R.id.action_optionsFragment_to_optionsElementsFragment)
            return true
        }

        return super.onPreferenceTreeClick(preference);
    }

}
