package com.sduduzog.slimlauncher.ui.options

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.databinding.OptionsFragmentBinding
import com.sduduzog.slimlauncher.ui.dialogs.ChangeThemeDialog
import com.sduduzog.slimlauncher.ui.dialogs.ChooseTimeFormatDialog
import com.sduduzog.slimlauncher.utils.BaseFragment

class OptionsFragment : BaseFragment() {

    private var _binding: OptionsFragmentBinding? = null
    private val binding get() = _binding
    override fun getFragmentView(): ViewGroup = binding!!.root

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OptionsFragmentBinding.inflate(inflater, container, false)
        binding!!.optionsFragmentDeviceSettings.setOnClickListener {
            val intent = Intent(Settings.ACTION_SETTINGS)
            launchActivity(it, intent)
        }
        binding!!.optionsFragmentDeviceSettings.setOnLongClickListener {
            val intent = Intent(Settings.ACTION_HOME_SETTINGS)
            launchActivity(it, intent)
            true
        }
        binding!!.optionsFragmentChangeTheme.setOnClickListener {
            val changeThemeDialog = ChangeThemeDialog.getThemeChooser()
            changeThemeDialog.showNow(childFragmentManager, "THEME_CHOOSER")
        }
        binding!!.optionsFragmentChooseTimeFormat.setOnClickListener {
            val chooseTimeFormatDialog = ChooseTimeFormatDialog.getInstance()
            chooseTimeFormatDialog.showNow(childFragmentManager, "TIME_FORMAT_CHOOSER")
        }
        binding!!.optionsFragmentToggleStatusBar.setOnClickListener {
            val settings = requireContext().getSharedPreferences(
                getString(R.string.prefs_settings),
                MODE_PRIVATE
            )
            val isHidden =
                settings.getBoolean(getString(R.string.prefs_settings_key_toggle_status_bar), false)
            settings.edit {
                putBoolean(getString(R.string.prefs_settings_key_toggle_status_bar), !isHidden)
            }
        }
        binding!!.optionsFragmentCustomiseApps.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_optionsFragment_to_customiseAppsFragment
            )
        )
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}