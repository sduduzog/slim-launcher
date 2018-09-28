package com.sduduzog.slimlauncher.ui.apps

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

class WhyQuestionDialog : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Why?")
        builder.setMessage("That's the point exactly!, this app is supposed to help you use less of your phone.")
        return builder.create()
    }
}