package com.sduduzog.slimlauncher.utils

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.AsyncTask
import android.os.Build
import android.provider.AlarmClock
import android.provider.MediaStore
import com.sduduzog.slimlauncher.BuildConfig
import com.sduduzog.slimlauncher.data.MainViewModel
import com.sduduzog.slimlauncher.data.model.App
import java.util.Collections

class LoadInstalledApps(private val viewModel: MainViewModel?, private val filterString: String = "") : AsyncTask<PackageManager, Unit, List<App>>() {

    private lateinit var packageManager: PackageManager

    override fun doInBackground(vararg params: PackageManager): List<App> {
        val pm = params[0]
        packageManager = pm
        val list = mutableListOf<App>()
        val main = Intent(Intent.ACTION_MAIN, null)
        main.addCategory(Intent.CATEGORY_LAUNCHER)
        val activitiesList = pm.queryIntentActivities(main, 0)
        Collections.sort(activitiesList, ResolveInfo.DisplayNameComparator(pm))
        activitiesList.indices.forEach {
            val item = activitiesList[it]
            val activity = item.activityInfo
            val app = App(
                    activitiesList[it].loadLabel(pm).toString(),
                    activity.applicationInfo.packageName, activity.name
            )
            list.add(app)
        }

        val filter = mutableListOf<String>()

        Intent(Intent.ACTION_DIAL).resolveActivity(packageManager)?.let {
            filter.add(it.packageName)
        }
        Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_CALENDAR).resolveActivity(packageManager)?.let {
            filter.add(it.packageName)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent(AlarmClock.ACTION_SHOW_ALARMS).resolveActivity(packageManager)?.let {
                filter.add(it.packageName)
            }
        }

        Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA).resolveActivity(packageManager)?.let {
            filter.add(it.packageName)
        }

        filter.add(BuildConfig.APPLICATION_ID)

        return list.filterNot { filter.contains(it.packageName) }
    }

    override fun onPostExecute(result: List<App>) {
        viewModel?.installedApps?.value = result.filter { it.appName.contains(filterString, true) }
    }
}