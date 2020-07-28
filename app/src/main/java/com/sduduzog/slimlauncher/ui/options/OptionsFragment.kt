package com.sduduzog.slimlauncher.ui.options

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.sduduzog.slimlauncher.R

class OptionsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.options_fragment, rootKey)
    }
}
