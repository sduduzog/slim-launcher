package com.sduduzog.slimlauncher.ui.main.notes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sduduzog.slimlauncher.data.DataRepository
import com.sduduzog.slimlauncher.data.Note

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private var _repository: DataRepository = DataRepository.getInstance(application)

    private var _notes: LiveData<List<Note>>

    init {
        _notes = _repository.notes
    }

    val notes: LiveData<List<Note>>
        get() = _notes

    fun saveNote(note: Note) {
        _repository.saveNote(note)
    }

    fun updateNote(note: Note) {
        _repository.updateNote(note)
    }

    fun deleteNote(note: Note) {
        _repository.deleteNote(note)
    }
}