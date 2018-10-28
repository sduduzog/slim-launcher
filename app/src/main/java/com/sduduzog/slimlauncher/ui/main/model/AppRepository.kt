package com.sduduzog.slimlauncher.ui.main.model

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import java.util.*

class AppRepository(application: Application) {

    private val db: AppRoomDatabase = AppRoomDatabase.getDatabase(application)!!
    private var appDao: AppDao = db.appDao()
    private var _apps: LiveData<List<App>> = appDao.apps
    private var _homeApps: LiveData<List<HomeApp>> = appDao.homeApps

    private var pm: PackageManager = application.packageManager

    val homeApps: LiveData<List<HomeApp>>
        get() = _homeApps

    val apps: LiveData<List<App>>
        get() = _apps

    fun insert(app: HomeApp) {
        InsertAsyncTask(appDao).execute(app)
    }

    fun delete(app: HomeApp) {
        DeleteAsyncTask(appDao).execute(app)
    }

    fun updateApps() {
        UpdateAppsAsyncTask(appDao).execute(pm)
    }

    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: AppDao) : AsyncTask<HomeApp, Void, Void>() {

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
                val app = App(launchables[i].loadLabel(pm).toString(), activity.applicationInfo.packageName, activity.name)
                mAsyncTaskDao.insert(app)
            }
            return null
        }
    }
}