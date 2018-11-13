package com.sduduzog.slimlauncher.ui.main.settings


import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sduduzog.slimlauncher.R
import kotlinx.android.synthetic.main.settings_fragment.*


class SettingsFragment : Fragment() {

    private lateinit var adapter: SettingsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = SettingsListAdapter(this)
        settingsAppList.adapter = adapter
        settingsAppList.layoutManager = LinearLayoutManager(activity)
        buttonChangeTheme.setOnClickListener {
            val themeChooserDialog = ThemeChooserDialog.getThemeChooser()
            themeChooserDialog.showNow(fragmentManager, "THEME_CHOOSER")
        }
        val settings = context!!.getSharedPreferences(getString(R.string.prefs_settings), MODE_PRIVATE)
        clockTypeChecker.isChecked = settings.getBoolean(getString(R.string.prefs_settings_key_clock_type), false)
        clockTypeChecker.setOnCheckedChangeListener { _, b ->
            settings.edit {
                putBoolean(getString(R.string.prefs_settings_key_clock_type), b)
            }
        }
    }

}
