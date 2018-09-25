package com.sduduzog.slimlauncher.ui.apps

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.App
import com.sduduzog.slimlauncher.data.HomeApp
import com.sduduzog.slimlauncher.ui.main.MainViewModel
import java.util.*


class AppsFragment : Fragment() {

    private var apps: MutableList<App> = mutableListOf()
    private lateinit var viewModel: MainViewModel
    private lateinit var mAdapter: AppsListAdapter
    private lateinit var layout: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_apps_list, container, false)
        val pm = activity!!.packageManager
        val main = Intent(Intent.ACTION_MAIN, null)

        main.addCategory(Intent.CATEGORY_LAUNCHER)

        val launchables = pm.queryIntentActivities(main, 0)
        Collections.sort(launchables,
                ResolveInfo.DisplayNameComparator(pm))
        for (i in launchables.indices) {
            val item = launchables[i]
            val activity = item.activityInfo
            val app = App(activity.loadLabel(pm).toString(), activity.name, activity.applicationInfo.packageName)
            apps.add(app)
        }
        mAdapter = AppsListAdapter(listOf(), InteractionHandler())
        layout = view.findViewById(R.id.appList)
        layout.adapter = mAdapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.apps.observe(this, Observer {
            if (it != null) {
                mAdapter.setList(it)
            }
        })
    }

    inner class InteractionHandler : OnListFragmentInteractionListener {
        override fun onListFragmentInteraction(app: App) {
            viewModel.addToHomeScreen(app)
            val nav = Navigation.findNavController(layout)
            nav.popBackStack()
        }
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(app: App)
    }
}
