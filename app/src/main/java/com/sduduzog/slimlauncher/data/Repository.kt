package com.sduduzog.slimlauncher.data

import android.app.Application

abstract class Repository(application: Application) {
    private val database: DataRoomDatabase = DataRoomDatabase.getDatabase(application)!!

    protected val appDao: AppDao = database.appDao()
    protected val noteDao: NoteDao = database.noteDao()
    protected val taskDao: TaskDao = database.taskDao()
}