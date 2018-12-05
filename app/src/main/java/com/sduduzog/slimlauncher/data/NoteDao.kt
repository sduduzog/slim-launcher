package com.sduduzog.slimlauncher.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @get:Query("SELECT * FROM notes ORDER BY id ASC")
    val notes: LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}