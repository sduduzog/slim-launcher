package com.sduduzog.slimlauncher.data

import android.app.Application
import android.arch.lifecycle.LiveData
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.AsyncTask
import java.util.*

class AppRepository(application: Application) {

    private val db: AppRoomDatabase = AppRoomDatabase.getDatabase(application)
    private var appDao: AppDao = db.appDao()
    private var _apps: LiveData<List<App>> = appDao.apps
    private var _homeApps: LiveData<List<HomeApp>> = appDao.homeApps

    private var pm: PackageManager = application.packageManager

    val homeApps: LiveData<List<HomeApp>>
        get() = _homeApps

    val apps: LiveData<List<App>>
    get() = _apps

    fun insert(app: App) {
        InsertAsyncTask(appDao).execute(app)
    }

    fun delete(app: HomeApp) {
        DeleteAsyncTask(appDao).execute(app)
    }

    fun update(app: App) {
        UpdateAsyncTask(appDao).execute(app)
    }

    fun updateApps() {
        UpdateAppsAsyncTask(appDao).execute(pm)
    }

    fun insertHomeApp(home: HomeApp) {
    InsertHomeAsyncTask(appDao).execute(home)
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: AppDao) : AsyncTask<App, Void, Void>() {

        override fun doInBackground(vararg params: App): Void? {
            mAsyncTaskDao.insert(params[0])
            return null
        }
    }

    private class InsertHomeAsyncTask internal constructor(private val mAsyncTaskDao: AppDao) : AsyncTask<HomeApp, Void, Void>() {

        override fun doInBackground(vararg params: HomeApp): Void? {
            mAsyncTaskDao.addHomeApp(params[0])
            return null
        }
    }

    private class DeleteAsyncTask internal constructor(private val mAsyncTaskDao: AppDao) : AsyncTask<HomeApp, Void, Void>() {

        override fun doInBackground(vararg params: HomeApp): Void? {
            mAsyncTaskDao.delete(params[0])
            return null
        }
    }

    private class UpdateAsyncTask internal constructor(private val mAsyncTaskDao: AppDao) : AsyncTask<App, Void, Void>() {

        override fun doInBackground(vararg params: App): Void? {
            mAsyncTaskDao.update(params[0])
            return null
        }
    }

    private class UpdateAppsAsyncTask internal constructor(private val mAsyncTaskDao: AppDao) : AsyncTask<PackageManager, Void, Void>() {

        override fun doInBackground(vararg params: PackageManager): Void? {
            val pm = params[0]
            val main = Intent(Intent.ACTION_MAIN, null)

            main.addCategory(Intent.CATEGORY_LAUNCHER)

            val launchables = pm.queryIntentActivities(main, 0)
            Collections.sort(launchables,
                    ResolveInfo.DisplayNameComparator(pm))
            mAsyncTaskDao.deleteAll()
            for (i in launchables.indices) {
                val item = launchables[i]
                val activity = item.activityInfo
                val app = App(activity.loadLabel(pm).toString(), activity.name, activity.applicationInfo.packageName)
                mAsyncTaskDao.insert(app)
            }
            return null
        }
    }
}