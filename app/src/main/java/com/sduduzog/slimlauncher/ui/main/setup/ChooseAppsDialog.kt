package com.sduduzog.slimlauncher.ui.main.setup

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.App
import java.util.*

class ChooseAppsDialog : DialogFragment() {
    private lateinit var listener: OnChooseAppsListener
    private lateinit var apps: MutableList<App>
    private lateinit var checkedItems: BooleanArray

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val pm = activity!!.packageManager
        val main = Intent(Intent.ACTION_MAIN, null)
        main.addCategory(Intent.CATEGORY_LAUNCHER)
        val launchables = pm.queryIntentActivities(main, 0)
        Collections.sort(launchables,
                ResolveInfo.DisplayNameComparator(pm))
        apps = mutableListOf()
        for (i in launchables.indices) {
            val item = launchables[i]
            val activity = item.activityInfo
            val app = App(launchables[i].loadLabel(pm).toString(), activity.applicationInfo.packageName, activity.name)
            apps.add(app)
        }
        val appNames = arrayOfNulls<String>(apps.size)
        checkedItems = BooleanArray(apps.size)
        for (i in apps.indices) {
            appNames[i] = apps[i].appName
        }

        val builder = AlertDialog.Builder(context!!)
        builder.setMultiChoiceItems(appNames, checkedItems) { _, i, b ->
            checkedItems[i] = b
            if (checkedItems.filter { it }.size == 5) {
                setApps()
                dismiss()
            }

        }
        builder.setPositiveButton("Done") { _, _ ->
            if (checkedItems.none { it }) {
                Toast.makeText(context, getString(R.string.no_app_selected_toast_msg), Toast.LENGTH_SHORT).show()
            } else {
                setApps()
            }
        }
        builder.setTitle(getString(R.string.choose_apps_title))

        return builder.create()
    }

    private fun setApps() {
        val list = mutableListOf<App>()
        for (i in checkedItems.indices) {
            if (checkedItems[i]) {
                list.add(apps[i])
            }
        }
        listener.onChooseApps(list)
    }

    companion object {

        fun getInstance(listener: OnChooseAppsListener): ChooseAppsDialog {
            return ChooseAppsDialog().apply {
                this.listener = listener
            }
        }

        interface OnChooseAppsListener {
            fun onChooseApps(apps: List<App>)
        }
    }
}