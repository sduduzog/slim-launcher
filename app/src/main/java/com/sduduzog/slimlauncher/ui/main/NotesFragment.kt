package com.sduduzog.slimlauncher.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.adapters.NotesAdapter
import com.sduduzog.slimlauncher.data.MainViewModel
import com.sduduzog.slimlauncher.ui.BaseFragment
import kotlinx.android.synthetic.main.notes_fragment.*

class NotesFragment : BaseFragment(){

    override fun getFragmentView(): View = notes_fragment

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.notes_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = NotesAdapter()
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        } ?: throw Error("Activity null, something here is fucked up")
        notes_fragment_list.adapter = adapter
    }
}