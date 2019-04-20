package com.sduduzog.slimlauncher.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.model.Note
import com.sduduzog.slimlauncher.utils.BaseFragment
import kotlinx.android.synthetic.main.note_fragment.*


class NoteFragment : BaseFragment() {

    override fun getFragmentView(): ViewGroup = note_fragment

    private lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            note = if (it != null && it.containsKey(getString(R.string.nav_key_note))) {
                it.get(getString(R.string.nav_key_note)) as Note
            } else {
                Note("")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.note_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        note.title?.let {
            note_fragment_title.text = it
        }
        note_fragment_body.text = note.body
        val bundle = Bundle()
        bundle.putSerializable(getString(R.string.nav_key_note), note)
        note_fragment_edit.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_noteFragment_to_editNoteFragment, bundle))
    }

    override fun onBack(): Boolean = false
}
