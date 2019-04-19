package com.sduduzog.slimlauncher.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.MainViewModel
import com.sduduzog.slimlauncher.data.model.HomeApp
import kotlinx.android.synthetic.main.customise_apps_fragment.*

class RenameAppDialog : DialogFragment() {

    private lateinit var app: HomeApp
    private lateinit var model: MainViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.rename_app_dialog_edit_text, customise_apps_fragment, false)
        val editText: EditText = view.findViewById(R.id.rename_editText)
        editText.text.append(app.appName)
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Rename ${app.appName}")
        builder.setView(view)
        builder.setPositiveButton("DONE") { _, _ ->
            val name = editText.text.toString()
            updateApp(name)
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
            app.appName = newName
            model.update(app)
        } else {
            Toast.makeText(context, "Couldn't save, App name shouldn't be empty", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        fun getInstance(app: HomeApp, model: MainViewModel): RenameAppDialog {
            return RenameAppDialog().apply {
                this.model = model
                this.app = app
            }
        }
    }
}