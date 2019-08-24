package com.sduduzog.slimlauncher.models

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.sduduzog.slimlauncher.data.BaseDao
import com.sduduzog.slimlauncher.data.BaseDatabase

class Repository(application: Application) {

    private val database: BaseDatabase = BaseDatabase.getDatabase(application)!!

    private val baseDao: BaseDao = database.baseDao()

    private val _apps = baseDao.apps

    val apps: LiveData<List<HomeApp>>
        get() = _apps

    fun add(app: HomeApp) {
        AddAppAsyncTask(baseDao).execute(app)
    }

    fun update(vararg list: HomeApp) {
        UpdateAppAsyncTask(baseDao).execute(*list)
    }

    fun remove(vararg app: HomeApp) {
        RemoveAppAsyncTask(baseDao).execute(*app)
    }

    private class AddAppAsyncTask(private val mAsyncTaskDao: BaseDao) : AsyncTask<HomeApp, Void, Void>() {

        override fun doInBackground(vararg params: HomeApp): Void? {
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

    private class RemoveAppAsyncTask(private val mAsyncTaskDao: BaseDao) : AsyncTask<HomeApp, Void, Void>() {

        override fun doInBackground(vararg params: HomeApp): Void? {
            mAsyncTaskDao.remove(*params)
            return null
        }
    }
}
