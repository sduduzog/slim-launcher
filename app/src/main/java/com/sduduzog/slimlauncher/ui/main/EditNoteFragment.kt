package com.sduduzog.slimlauncher.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.utils.BaseFragment
import kotlinx.android.synthetic.main.edit_note_fragment.*

class EditNoteFragment : BaseFragment() {

    override fun getFragmentView(): ViewGroup = edit_note_fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.edit_note_fragment, container, false)
    }
}