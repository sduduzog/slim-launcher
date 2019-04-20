package com.sduduzog.slimlauncher.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.MainViewModel
import com.sduduzog.slimlauncher.data.model.Note
import com.sduduzog.slimlauncher.utils.BaseFragment
import kotlinx.android.synthetic.main.edit_note_fragment.*
import java.security.MessageDigest
import java.util.*

class EditNoteFragment : BaseFragment() {

    override fun getFragmentView(): ViewGroup = edit_note_fragment
    private lateinit var note: Note
    private lateinit var viewModel: MainViewModel
    private lateinit var initialDigest: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            note = if (it != null && it.containsKey(getString(R.string.nav_key_note))) {
                it.get(getString(R.string.nav_key_note)) as Note
            } else {
                Note("")
            }
        }
        initialDigest = hash(note.title + note.body)
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        } ?: throw Error("Activity null, something here is fucked up")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.edit_note_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (note.edited == -1L) {
            // Wait
        } else {
            edit_note_fragment_title.setText(note.title.orEmpty())
            edit_note_fragment_body.setText(note.body)
        }

        if (edit_note_fragment_body.requestFocus()) {
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(edit_note_fragment_title, InputMethodManager.SHOW_IMPLICIT)
        }
        edit_note_fragment_save.setOnClickListener {
            save()?.let {
                val bundle = Bundle()
                bundle.putSerializable(getString(R.string.nav_key_note), it)
                Log.d("check", "$it")
                Navigation.findNavController(edit_note_fragment).navigate(R.id.action_editNoteFragment_to_noteFragment, bundle)
            }
        }
    }

    private fun hash(input: String): String {
        val bytes = input.toByteArray(charset("UTF-8"))
        val md = MessageDigest.getInstance("MD5")
        md.update(bytes)
        return String(md.digest())
    }

    private fun save(): Note? {
        val body = edit_note_fragment_body.text.toString()
        val title = edit_note_fragment_title.text.toString()
        val newNote = Note(body, Date().time)
        newNote.title = title
        newNote.body = body.trim()
        newNote.id = note.id
        val currentDigest = hash(newNote.title + newNote.body)
        if (body.isEmpty()) return null
        if (initialDigest == currentDigest) return null
        if (note.id == null) viewModel.add(newNote) else viewModel.update(newNote)
        return newNote
    }
}