package com.sduduzog.slimlauncher.ui.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import com.sduduzog.slimlauncher.R

@SuppressLint("ValidFragment")
class ThemeChooserDialog(private val ctx: Context) : DialogFragment() {

    private val settings: SharedPreferences = ctx.getSharedPreferences("settings", MODE_PRIVATE)
    private var choice: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle("Choose theme")


        val active = settings.getInt("theme", 0)
        builder.setSingleChoiceItems(R.array.themes_array, active) { _, i -> choice = i }.setPositiveButton("Done") { _, i -> Log.d("DIALOG", "val$i") }
        return builder.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        settings.edit().putInt("theme", choice).apply()
    }
}
