package com.sduduzog.slimlauncher.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.sduduzog.slimlauncher.data.model.Note

class NoteRepository(application: Application) : Repository(application) {

    private val _notes = noteDao.notes

    val notes: LiveData<List<Note>>
        get() = _notes

    fun add(args: Note) {
        AddNoteAsyncTask(noteDao).execute(args)
    }

    fun update(vararg args: Note) {
        UpdateNotesAsyncTask(noteDao).execute(*args)
    }

    fun remove(note: Note) {
        RemoveNoteAsyncTask(noteDao).execute(note)
    }

    private class AddNoteAsyncTask(private val mAsyncTaskDao: NoteDao) : AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg params: Note): Void? {
            mAsyncTaskDao.add(params[0])
            return null
        }
    }

    private class UpdateNotesAsyncTask(private val mAsyncTaskDao: NoteDao) : AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg params: Note): Void? {
            mAsyncTaskDao.update(*params)
            return null
        }
    }

    private class RemoveNoteAsyncTask(private val mAsyncTaskDao: NoteDao) : AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg params: Note): Void? {
            mAsyncTaskDao.remove(*params)
            return null
        }
    }
}