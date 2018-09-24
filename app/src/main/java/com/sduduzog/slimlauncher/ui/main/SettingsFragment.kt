package com.sduduzog.slimlauncher.ui.main


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: SettingsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.apps.observe(this, Observer {
            if (it != null) {
                adapter.setApps(it)
                when (it.size) {
                    in 0..4 -> addButton.visibility = View.VISIBLE
                    else -> addButton.visibility = View.GONE
                }
            }
        })
        var apps = viewModel.apps.value
        if (apps == null)
            apps = listOf()
        adapter = SettingsListAdapter(apps, InteractionHandler())
        settingsAppList.adapter = adapter
        settingsAppList.layoutManager = LinearLayoutManager(activity)
        deviceSettingsButton.setOnClickListener {
            startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0)
        }
        val settings = activity?.getSharedPreferences("settings", MODE_PRIVATE)
        val active = settings?.getBoolean("theme", false)
        themeSwitch.isChecked = active!!
        themeSwitch.setOnCheckedChangeListener { _, b ->

            settings.edit()?.putBoolean("theme", b)?.apply()
        }
        addButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_openAppsFragment))
    }

    inner class InteractionHandler : OnListFragmentInteractionListener {
        override fun onRemove(packageName: String) {
            viewModel.deleteApp(packageName)
        }
    }

    interface OnListFragmentInteractionListener {
        fun onRemove(packageName: String)
    }
}
