package com.sduduzog.slimlauncher.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.sduduzog.slimlauncher.MainActivity.Companion.REQUEST_CODE_ENABLE_ADMIN
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.SlimAdminReceiver


class MakeSlimAdminDialog : DialogFragment() {

    private var mComponentName: ComponentName? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mComponentName = ComponentName(context!!, SlimAdminReceiver::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context!!)

        builder.setTitle(getString(R.string.main_admin_title))
        builder.setMessage(getString(R.string.main_admin_message))
        builder.setNegativeButton("no, thanks") { _, _ -> }
        builder.setPositiveButton("activate") { _, _ ->
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName)
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.main_admin_message))
            startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN)
        }
        return builder.create()
    }
}