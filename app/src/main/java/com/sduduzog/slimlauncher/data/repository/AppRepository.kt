package com.sduduzog.slimlauncher.data.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.sduduzog.slimlauncher.data.dao.AppDao
import com.sduduzog.slimlauncher.data.entity.HomeApp
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(private val appDao: AppDao) {

    private val _apps = appDao.apps

    val apps: LiveData<List<HomeApp>>
        get() = _apps

    fun add(app: HomeApp) {
        AddAppAsyncTask(appDao).execute(app)
    }

    fun update(vararg list: HomeApp) {
        UpdateAppAsyncTask(appDao).execute(*list)
    }

    fun remove(vararg app: HomeApp) {
        RemoveAppAsyncTask(appDao).execute(*app)
    }

    private class AddAppAsyncTask(private val mAsyncTaskDao: AppDao) : AsyncTask<HomeApp, Void, Void>() {

        override fun doInBackground(vararg params: HomeApp): Void? {
            mAsyncTaskDao.add(params[0])
            return null
        }
    }

    private class UpdateAppAsyncTask(private val mAsyncTaskDao: AppDao) : AsyncTask<HomeApp, Void, Void>() {

        override fun doInBackground(vararg params: HomeApp): Void? {
            mAsyncTaskDao.update(*params)
            return null
        }
    }

    private class RemoveAppAsyncTask(private val mAsyncTaskDao: AppDao) : AsyncTask<HomeApp, Void, Void>() {

        override fun doInBackground(vararg params: HomeApp): Void? {
            mAsyncTaskDao.remove(*params)
            return null
        }
    }
}
