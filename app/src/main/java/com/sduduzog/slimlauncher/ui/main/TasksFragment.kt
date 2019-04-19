package com.sduduzog.slimlauncher.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.adapters.TasksAdapter
import com.sduduzog.slimlauncher.data.MainViewModel
import com.sduduzog.slimlauncher.data.model.Task
import com.sduduzog.slimlauncher.utils.BaseFragment
import kotlinx.android.synthetic.main.tasks_fragment.*

class TasksFragment : BaseFragment() {

    override fun getFragmentView(): ViewGroup = tasks_fragment

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tasks_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        } ?: throw Error("Activity null, something here is fucked up")
        val adapter = TasksAdapter(context!!, viewModel)
        tasks_fragment_list.adapter = adapter
        viewModel.tasks.observe(this, Observer {
            it?.let { list ->
                adapter.setItems(list)
            }
        })

        tasks_fragment_input.setOnEditorActionListener { v, _, _ ->
            val body = v.text.toString()
            val task = Task(body, false, viewModel.tasks.value.orEmpty().size)
            if (body.isNotBlank()) viewModel.add(task)
            v.text = ""
            true
        }
    }
}