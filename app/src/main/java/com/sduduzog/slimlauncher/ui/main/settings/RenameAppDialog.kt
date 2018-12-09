package com.sduduzog.slimlauncher.ui.main.settings

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.HomeApp
import com.sduduzog.slimlauncher.ui.main.MainViewModel
import kotlinx.android.synthetic.main.settings_fragment.*

class RenameAppDialog : DialogFragment() {

    private lateinit var app: HomeApp
    private lateinit var model: MainViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.settings_rename_app, settingsAppList)
        val editText = view.findViewById<EditText>(R.id.rename_editText)
        editText.text.append(app.appName)
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Rename ${app.appName}")
        builder.setView(view)
        builder.setPositiveButton("DONE") { _, _ ->
            app.appName = editText.text.toString()
            model.renameApp(app)
        }
        return builder.create()
    }

    companion object {
        fun rename(app: HomeApp, model: MainViewModel): RenameAppDialog {
            return RenameAppDialog().apply {
                this.model = model
                this.app = app
            }
        }
    }
}