package com.sduduzog.slimlauncher.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.adapters.TasksAdapter
import com.sduduzog.slimlauncher.data.MainViewModel
import com.sduduzog.slimlauncher.utils.BaseFragment
import kotlinx.android.synthetic.main.tasks_fragment.*

class TasksFragment : BaseFragment() {

    override fun getFragmentView(): View = tasks_fragment

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tasks_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = TasksAdapter()
        tasks_fragment_list.adapter = adapter

        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        } ?: throw Error("Activity null, something here is fucked up")
    }
}