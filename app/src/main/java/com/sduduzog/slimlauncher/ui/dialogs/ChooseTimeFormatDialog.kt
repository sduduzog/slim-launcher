package com.sduduzog.slimlauncher.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.sduduzog.slimlauncher.AppConstants
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.ui.options.OptionsViewModel

class ChooseTimeFormatDialog : DialogFragment() {

    private lateinit var settings: SharedPreferences
    internal lateinit var timeFormat: String
    internal lateinit var optionsViewModel: OptionsViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val defaultTimeFormat = timeFormat == AppConstants.DEFAULT_TIME_FORMAT
        val builder = AlertDialog.Builder(context!!)
        settings = context!!.getSharedPreferences(getString(R.string.prefs_settings), MODE_PRIVATE)
        val index = if (defaultTimeFormat) 1 else 0
        builder.setTitle(R.string.choose_time_format_dialog_title)
        builder.setSingleChoiceItems(R.array.time_format_array, index) { dialogInterface, i ->
            dialogInterface.dismiss()
            val format = if (i == 1) AppConstants.DEFAULT_TIME_FORMAT
            else AppConstants.SECONDARY_TIME_FORMAT
            optionsViewModel.setTimeFormat(format)

        }
        return builder.create()
    }

    companion object {
        fun getInstance(timeFormat: String, optionsViewModel: OptionsViewModel): ChooseTimeFormatDialog {
            return ChooseTimeFormatDialog().apply {
                this.timeFormat = timeFormat
                this.optionsViewModel = optionsViewModel
            }
        }
    }
}