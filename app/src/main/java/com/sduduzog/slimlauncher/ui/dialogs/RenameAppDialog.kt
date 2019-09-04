package com.sduduzog.slimlauncher.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.entity.HomeApp
import com.sduduzog.slimlauncher.ui.options.CustomiseAppsViewModel
import kotlinx.android.synthetic.main.customise_apps_fragment.*

class RenameAppDialog : DialogFragment() {

    internal lateinit var app: HomeApp
    internal lateinit var model: CustomiseAppsViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.rename_dialog_edit_text, customise_apps_fragment, false)
        val editText: EditText = view.findViewById(R.id.rename_editText)
        val appName: String = app.appNickname ?: app.appName
        editText.text.append(appName)
        val builder = AlertDialog.Builder(context!!).apply {
            setTitle("Rename $appName")
            setView(view)
            setPositiveButton("DONE") { _, _ ->
                val name = editText.text.toString()
                updateApp(name)
            }
        }
        editText.setOnEditorActionListener { v, _, _ ->
            val name = v.text.toString()
            updateApp(name)
            this@RenameAppDialog.dismiss()
            true
        }
        return builder.create()
    }

    private fun updateApp(newName: String) {
        if (newName.isNotEmpty()) {
            app.appNickname = newName
            model.update(app)
        } else {
            Toast.makeText(context, "Couldn't save, App name shouldn't be empty", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        fun getInstance(app: HomeApp, model: CustomiseAppsViewModel) = RenameAppDialog(app, model)
    }
}