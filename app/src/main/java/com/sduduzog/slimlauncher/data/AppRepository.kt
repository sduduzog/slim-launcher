package com.sduduzog.slimlauncher.data

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

class AppRepository(application: Application){

    val db = AppRoomDatabase.getDatabase(application)
    private var appDao: AppDao = db!!.appDao()
    private var _apps: LiveData<List<App>> = appDao.allApps
    private var _homeApps: LiveData<List<App>> = appDao.homeApps
    private var _availableApps: LiveData<List<App>> = appDao.availableApps


    val allApps: LiveData<List<App>>
        get() = _apps

    val homeApps: LiveData<List<App>>
    get() = _homeApps

    val availableApps: LiveData<List<App>>
        get() = _availableApps

    fun insert(app: App) {
        InsertAsyncTask(appDao).execute(app)
    }

    fun update(app: App) {
        UpdateAsyncTask(appDao).execute(app)
    }

    fun bulkInsert(apps: List<App>) {
        BulkInsertAsyncTask(appDao).execute(apps)
    }

    fun delete(packageName: String) {
        DeleteAsyncTask(appDao).execute(packageName)
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: AppDao) : AsyncTask<App, Void, Void>() {

        override fun doInBackground(vararg params: App): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    private class UpdateAsyncTask internal constructor(private val mAsyncTaskDao: AppDao) : AsyncTask<App, Void, Void>() {

        override fun doInBackground(vararg params: App): Void? {
            mAsyncTaskDao.update(params[0])
            return null
        }
    }

    private class BulkInsertAsyncTask internal constructor(private val mAsyncTaskDao: AppDao) : AsyncTask<List<App>, Void, Void>() {

        override fun doInBackground(vararg params: List<App>): Void? {
            mAsyncTaskDao.deleteAll()
            val apps = params[0]
            for (i in apps.indices){
                mAsyncTaskDao.insert(apps[i])
            }

            return null
        }
    }

    private class DeleteAsyncTask internal constructor(private val mAsyncTaskDao: AppDao) : AsyncTask<String, Void, Void>() {

        override fun doInBackground(vararg params: String): Void? {
            mAsyncTaskDao.delete(params[0])
            return null
        }
    }
}