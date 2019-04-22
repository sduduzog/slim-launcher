package com.sduduzog.slimlauncher.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.MainViewModel
import com.sduduzog.slimlauncher.data.model.Note
import kotlinx.android.synthetic.main.voice_note_fragment.*

class RenameNoteDialog : DialogFragment() {

    private lateinit var note: Note
    private lateinit var viewModel: MainViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.rename_dialog_edit_text, voice_note_fragment, false)
        val editText: EditText = view.findViewById(R.id.rename_editText)
        editText.text.append(note.title)
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Rename Audio")
        builder.setView(view)
        builder.setPositiveButton("DONE") { _, _ ->
            val name = editText.text.toString()
            updateNote(name)
        }
        editText.setOnEditorActionListener { v, _, _ ->
            val title = v.text.toString()
            updateNote(title)
            this@RenameNoteDialog.dismiss()
            true
        }
        return builder.create()
    }

    private fun updateNote(newTitle: String) {
        if (newTitle.isNotEmpty()) {
            note.title = newTitle
            viewModel.update(note)
        } else {
            Toast.makeText(context, "Couldn't save, title shouldn't be empty", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        fun getInstance(note: Note, viewModel: MainViewModel): RenameNoteDialog {
            return RenameNoteDialog().apply {
                this.note = note
                this.viewModel = viewModel
            }
        }
    }
}