package com.sduduzog.slimlauncher.ui.main


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.ui.main.model.MainViewModel
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.ui.main.model.HomeApp
import kotlinx.android.synthetic.main.settings_fragment.*


class SettingsFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: SettingsListAdapter

    private val TAG: String = "SettingsFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.homeApps.observe(this, Observer {
            if (it != null) {
                adapter.setApps(it)
                when (it.size) {
                    in 0..4 -> addButton.visibility = View.VISIBLE
                    else -> addButton.visibility = View.GONE
                }
            }
        })
        var apps = viewModel.homeApps.value
        if (apps == null)
            apps = listOf()
        adapter = SettingsListAdapter(apps, InteractionHandler())
        settingsAppList.adapter = adapter
        settingsAppList.layoutManager = LinearLayoutManager(activity)
        addButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_openAppsFragment))
        changeThemeText.setOnClickListener {
            val themeChooserDialog = ThemeChooserDialog.getThemeChooser()
            themeChooserDialog.showNow(fragmentManager, TAG)
        }
        val settings = context!!.getSharedPreferences(getString(R.string.prefs_settings), MODE_PRIVATE)
        clockTypeChecker.isChecked = settings.getBoolean(getString(R.string.prefs_settings_key_clock_type), false)
        clockTypeChecker.setOnCheckedChangeListener { _, b ->
            settings.edit {
                putBoolean(getString(R.string.prefs_settings_key_clock_type), b)
            }
        }
    }

    inner class InteractionHandler : OnListFragmentInteractionListener {
        override fun onRemove(app: HomeApp) {
            viewModel.deleteApp(app)
        }
    }

    interface OnListFragmentInteractionListener {
        fun onRemove(app: HomeApp)
    }
}
