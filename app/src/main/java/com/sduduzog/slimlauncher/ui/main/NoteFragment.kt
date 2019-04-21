package com.sduduzog.slimlauncher.ui.main


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.MainViewModel
import com.sduduzog.slimlauncher.data.model.Note
import com.sduduzog.slimlauncher.utils.BaseFragment
import kotlinx.android.synthetic.main.note_fragment.*


class NoteFragment : BaseFragment() {

    override fun getFragmentView(): ViewGroup = note_fragment
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.note_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        } ?: throw Error("This is just dumb")
        var id = 0L
        arguments.let {
            id = if (it != null && it.containsKey(getString(R.string.nav_key_note))) {
                it.getLong(getString(R.string.nav_key_note))
            } else throw Exception("How did we get here??")
        }
        viewModel.notes.observe(this, Observer {
            it?.let { notes ->
                notes.firstOrNull { n -> n.id == id }?.let { note -> loadNote(note) }
            }
        })
    }

    private fun loadNote(note: Note) {
        note.title?.let { title -> if (title.isNotBlank()) note_fragment_title.text = title }
        note_fragment_body.text = note.body
        val bundle = Bundle()
        bundle.putSerializable(getString(R.string.nav_key_note), note)
        note_fragment_edit.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_noteFragment_to_editNoteFragment, bundle))
    }

    override fun onBack(): Boolean = false
}
