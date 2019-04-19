package com.sduduzog.slimlauncher.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.sduduzog.slimlauncher.data.model.HomeApp
import com.sduduzog.slimlauncher.data.model.Note
import com.sduduzog.slimlauncher.data.model.Task

class Repository(application: Application) {
    private val database: BaseDatabase = BaseDatabase.getDatabase(application)!!

    private val baseDao: BaseDao = database.baseDao()

    private val _apps = baseDao.apps
    private val _notes = baseDao.notes
    private val _tasks = baseDao.tasks


    val apps: LiveData<List<HomeApp>>
        get() = _apps

    val notes: LiveData<List<Note>>
        get() = _notes

    val tasks: LiveData<List<Task>>
        get() = _tasks

    fun add(app: HomeApp) {
        AddAppAsyncTask(baseDao).execute(app)
    }

    fun add(args: Note) {
        AddNoteAsyncTask(baseDao).execute(args)
    }

    fun add(args: Task) {
        AddTaskAsyncTask(baseDao).execute(args)
    }

    fun update(vararg list: HomeApp) {
        UpdateAppAsyncTask(baseDao).execute(*list)
    }

    fun update(vararg args: Note) {
        UpdateNotesAsyncTask(baseDao).execute(*args)
    }

    fun update(vararg args: Task) {
        UpdateTaskAsyncTask(baseDao).execute(*args)
    }

    fun remove(app: HomeApp) {
        RemoveAppAsyncTask(baseDao).execute(app)
    }

    fun remove(note: Note) {
        RemoveNoteAsyncTask(baseDao).execute(note)
    }

    private class AddAppAsyncTask(private val mAsyncTaskDao: BaseDao) : AsyncTask<HomeApp, Void, Void>() {

        override fun doInBackground(vararg params: HomeApp): Void? {
            mAsyncTaskDao.add(params[0])
            return null
        }
    }

    private class AddNoteAsyncTask(private val mAsyncTaskDao: BaseDao) : AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg params: Note): Void? {
            mAsyncTaskDao.add(params[0])
            return null
        }
    }

    private class AddTaskAsyncTask(private val mAsyncTaskDao: BaseDao) : AsyncTask<Task, Void, Void>() {

        override fun doInBackground(vararg params: Task): Void? {
            mAsyncTaskDao.add(params[0])
            return null
        }
    }

    private class UpdateAppAsyncTask(private val mAsyncTaskDao: BaseDao) : AsyncTask<HomeApp, Void, Void>() {

        override fun doInBackground(vararg params: HomeApp): Void? {
            mAsyncTaskDao.update(*params)
            return null
        }
    }

    private class UpdateNotesAsyncTask(private val mAsyncTaskDao: BaseDao) : AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg params: Note): Void? {
            mAsyncTaskDao.update(*params)
            return null
        }
    }

    private class UpdateTaskAsyncTask(private val mAsyncTaskDao: BaseDao) : AsyncTask<Task, Void, Void>() {

        override fun doInBackground(vararg params: Task): Void? {
            mAsyncTaskDao.update(*params)
            return null
        }
    }

    private class RemoveAppAsyncTask(private val mAsyncTaskDao: BaseDao) : AsyncTask<HomeApp, Void, Void>() {

        override fun doInBackground(vararg params: HomeApp): Void? {
            mAsyncTaskDao.remove(*params)
            return null
        }
    }

    private class RemoveNoteAsyncTask(private val mAsyncTaskDao: BaseDao) : AsyncTask<Note, Void, Void>() {

        override fun doInBackground(vararg params: Note): Void? {
            mAsyncTaskDao.remove(*params)
            return null
        }
    }
}