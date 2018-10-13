package com.sduduzog.slimlauncher.ui.main

import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.ui.main.model.App
import com.sduduzog.slimlauncher.ui.main.model.MainViewModel
import kotlinx.android.synthetic.main.apps_fragment.*
import java.util.*


class AppsFragment : Fragment() {

    private var apps: MutableList<App> = mutableListOf()
    private lateinit var viewModel: MainViewModel
    private lateinit var mAdapter: AppsListAdapter
    private lateinit var layout: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.apps_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.apps.observe(this, Observer {
            if (it != null) {
                mAdapter.setList(it)
            }
        })
        val pm = activity!!.packageManager
        val main = Intent(Intent.ACTION_MAIN, null)

        main.addCategory(Intent.CATEGORY_LAUNCHER)

        val launchables = pm.queryIntentActivities(main, 0)
        Collections.sort(launchables,
                ResolveInfo.DisplayNameComparator(pm))
        for (i in launchables.indices) {
            val item = launchables[i]
            val activity = item.activityInfo
            val app = App(launchables[i].loadLabel(pm).toString(), activity.applicationInfo.packageName, activity.name)
            apps.add(app)
        }
        mAdapter = AppsListAdapter(listOf(), InteractionHandler())
        appList.adapter = mAdapter
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
