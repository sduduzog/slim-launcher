package com.sduduzog.slimlauncher.ui.main.notes


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.Note
import kotlinx.android.synthetic.main.note_fragment.*


class NoteFragment : Fragment() {

    @Suppress("PropertyName")
    val TAG: String = "NoteFragment"

    private lateinit var note: Note
    private lateinit var viewModel: NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            note = if (it!!.containsKey("note")) {
                it.get("note") as Note
            } else {
                Note("", -1L)
            }
        }
        Log.d(TAG, "$note")
        viewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.note_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (note.edited == -1L) {
            if (bodyEditText.requestFocus()) {
                val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(bodyEditText, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        saveNote()
    }

    private fun saveNote() {
        val body = bodyEditText.text.toString()
        val title = titleEditText.text.toString()
        if (title.isNotEmpty()){
            note.title = title
        }
        if (body.isNotEmpty()) {
            note.body = body
            viewModel.saveNote(note)
        }
    }
}
