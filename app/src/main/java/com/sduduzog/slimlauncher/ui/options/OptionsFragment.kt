package com.sduduzog.slimlauncher.ui.options

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.dialogs.ChangeThemeDialog
import com.sduduzog.slimlauncher.dialogs.ChooseTimeFormatDialog
import com.sduduzog.slimlauncher.ui.BaseFragment
import kotlinx.android.synthetic.main.options_fragment.*

class OptionsFragment : BaseFragment() {
    override fun getFragmentView(): View = options_fragment as View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.options_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        options_fragment_about_slim.setOnClickListener {
            Toast.makeText(context, "Coming soon!", Toast.LENGTH_LONG).show()
        }
        options_fragment_device_settings.setOnClickListener {
            val intent = Intent(Settings.ACTION_SETTINGS)
            launchActivity(it, intent)
        }
        options_fragment_change_theme.setOnClickListener {
            val changeThemeDialog = ChangeThemeDialog.getThemeChooser()
            changeThemeDialog.showNow(fragmentManager, "THEME_CHOOSER")
        }
        options_fragment_choose_time_format.setOnClickListener {
            val chooseTimeFormatDialog = ChooseTimeFormatDialog.getInstance()
            chooseTimeFormatDialog.showNow(fragmentManager, "TIME_FORMAT_CHOOSER")
        }
        options_fragment_toggle_status_bar.setOnClickListener {
            val settings = context!!.getSharedPreferences(getString(R.string.prefs_settings), MODE_PRIVATE)
            val isHidden = settings.getBoolean(getString(R.string.prefs_settings_key_toggle_status_bar), false)
            settings.edit{
                putBoolean(getString(R.string.prefs_settings_key_toggle_status_bar), !isHidden)
            }
        }
        options_fragment_customise_apps.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_optionsFragment_to_customiseAppsFragment))
    }
}