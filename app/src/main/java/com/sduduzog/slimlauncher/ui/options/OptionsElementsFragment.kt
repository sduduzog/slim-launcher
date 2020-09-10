package com.sduduzog.slimlauncher.ui.options

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.sduduzog.slimlauncher.R

class OptionsElementsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.options_elements_fragment, rootKey)
    }
}
