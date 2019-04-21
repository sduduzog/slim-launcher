package com.sduduzog.slimlauncher.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.utils.BaseFragment
import kotlinx.android.synthetic.main.voice_note_fragment.*

class VoiceNoteFragment : BaseFragment() {
    override fun getFragmentView(): ViewGroup = voice_note_fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.voice_note_fragment, container, false)
    }
}