package com.sduduzog.slimlauncher.ui.options

import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.ui.dialogs.ChooseTimeFormatDialog

class OptionsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.options_fragment, rootKey)
        }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        if (preference?.key.equals("customizeApps")){
            Log.i("VINCENT", "DIT WERKT")
            val navigator = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
            navigator.navigate(R.id.action_optionsFragment_to_customiseAppsFragment)
            return true
        }

        if (preference?.key.equals("theme")){
            val chooseTimeFormatDialog = ChooseTimeFormatDialog.getInstance()
            chooseTimeFormatDialog.showNow(childFragmentManager, "TIME_FORMAT_CHOOSER")
            return true
        }

        return true
    }
}
