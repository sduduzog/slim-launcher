package com.sduduzog.slimlauncher.ui.main


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.MainViewModel
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.HomeApp
import kotlinx.android.synthetic.main.settings_fragment.*


class SettingsFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: SettingsListAdapter

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
