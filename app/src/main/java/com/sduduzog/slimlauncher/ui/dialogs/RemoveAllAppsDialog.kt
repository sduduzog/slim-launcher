package com.sduduzog.slimlauncher.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.entity.HomeApp
import com.sduduzog.slimlauncher.ui.options.CustomiseAppsViewModel

class RemoveAllAppsDialog : DialogFragment() {

    internal lateinit var apps: List<HomeApp>
    internal lateinit var model: CustomiseAppsViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(R.string.remove_all_apps_dialog_title)
        builder.setMessage(R.string.remove_all_apps_dialog_message)
        builder.setPositiveButton("OK") { _, _ ->
            model.remove(*apps.toTypedArray())
        }
        return builder.create()
    }

    companion object {
        fun getInstance(apps: List<HomeApp>, model: CustomiseAppsViewModel): RemoveAllAppsDialog {
            return RemoveAllAppsDialog().apply {
                this.apps = apps
                this.model = model
            }
        }
    }
}