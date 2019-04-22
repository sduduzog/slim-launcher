package com.sduduzog.slimlauncher.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class HomeWatcher(private val context: Context) {

    private var listener: OnHomePressedListener? = null
    private var receiver: InnerReceiver? = null
    private val filter = IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)

    fun setOnHomePressedListener(listener: OnHomePressedListener) {
        this.listener = listener
        receiver = InnerReceiver()
    }

    fun startWatch() {
        receiver?.let {
            context.registerReceiver(it, filter)
        }
    }

    fun stopWatch() {
        receiver?.let {
            context.unregisterReceiver(it)
        }
    }

    @Suppress("PrivatePropertyName")
    inner class InnerReceiver : BroadcastReceiver() {

        private val SYSTEM_DIALOG_REASON_KEY = "reason"
        private val SYSTEM_DIALOG_REASON_HOME_KEY = "homekey"

        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent == null) return
            if (intent.action != Intent.ACTION_CLOSE_SYSTEM_DIALOGS) return
            val reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY) ?: return
            if (reason != SYSTEM_DIALOG_REASON_HOME_KEY) return
            listener?.onHomePressed()
        }
    }

    interface OnHomePressedListener {
        fun onHomePressed()
    }
}