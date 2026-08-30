package com.sduduzog.slimlauncher.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat

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
            ContextCompat.registerReceiver(
                context,
                it,
                filter,
                ContextCompat.RECEIVER_EXPORTED
            )
        }
    }

    fun stopWatch() {
        receiver?.let {
            context.unregisterReceiver(it)
        }
    }

    inner class InnerReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent == null) return
            if (intent.action != Intent.ACTION_CLOSE_SYSTEM_DIALOGS) return
            val reason = intent.getStringExtra("reason") ?: return
            if (reason != "homekey") return
            listener?.onHomePressed()
        }
    }

    interface OnHomePressedListener {
        fun onHomePressed()
    }
}