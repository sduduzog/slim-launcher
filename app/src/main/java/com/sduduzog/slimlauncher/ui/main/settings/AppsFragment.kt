package com.sduduzog.slimlauncher.ui.main.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.ui.main.model.MainViewModel
import kotlinx.android.synthetic.main.apps_fragment.*


class AppsFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var mAdapter: AppsListAdapter
    private var appIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.apply {
            appIndex = this!!.getInt("index")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.apps_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        mAdapter = AppsListAdapter(this, InteractionHandler(), appIndex)
        appList.adapter = mAdapter
    }

    inner class InteractionHandler : OnAppsChooserListener {
        override fun onAppChosen() {
            val nav = Navigation.findNavController(appList)
            nav.popBackStack()
        }
    }

    interface OnAppsChooserListener {
        fun onAppChosen()
    }
}
