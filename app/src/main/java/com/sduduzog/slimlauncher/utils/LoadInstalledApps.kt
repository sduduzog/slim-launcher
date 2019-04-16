package com.sduduzog.slimlauncher.utils

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.AsyncTask
import com.sduduzog.slimlauncher.data.App
import com.sduduzog.slimlauncher.data.MainViewModel
import java.util.*

class LoadInstalledApps(val viewModel: MainViewModel) : AsyncTask<PackageManager, Unit, List<App>>() {
    override fun doInBackground(vararg params: PackageManager): List<App> {
        val pm = params[0]
        val list = mutableListOf<App>()
        val main = Intent(Intent.ACTION_MAIN, null)
        main.addCategory(Intent.CATEGORY_LAUNCHER)
        val activitiesList = pm.queryIntentActivities(main, 0)
        Collections.sort(activitiesList, ResolveInfo.DisplayNameComparator(pm))
        for (i in activitiesList.indices) {
            val item = activitiesList[i]
            val activity = item.activityInfo
            val app = App(activitiesList[i].loadLabel(pm).toString(), activity.applicationInfo.packageName, activity.name)
            list.add(app)
        }
        return list
    }

    override fun onPostExecute(result: List<App>) {
        viewModel.installedApps.value = result
    }
}