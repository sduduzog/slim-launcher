package com.sduduzog.slimlauncher.ui.main


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sduduzog.slimlauncher.R
import kotlinx.android.synthetic.main.home_framgent.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    @Suppress("PropertyName")
    val TAG: String = "HomeFragment"

    private lateinit var adapter: HomeAppsAdapter
    private lateinit var receiver: BroadcastReceiver

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.home_framgent, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated")
        clockTextView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val intent = Intent(android.provider.AlarmClock.ACTION_SHOW_ALARMS)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
        adapter = HomeAppsAdapter(this)
        mainAppsList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
        receiver = ClockReceiver()
        activity?.registerReceiver(receiver, IntentFilter(Intent.ACTION_TIME_TICK))
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        updateUi()
    }

    override fun onStop() {
        super.onStop()
        activity?.unregisterReceiver(receiver)
    }

    inner class ClockReceiver : BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent?) {
            updateUi()
        }
    }

    fun updateUi() {
        val twenty4Hour = context?.getSharedPreferences(getString(R.string.prefs_settings), Context.MODE_PRIVATE)
                ?.getBoolean(getString(R.string.prefs_settings_key_clock_type), false)
        val date = Date()
        if (twenty4Hour as Boolean) {
            val fWatchTime = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            clockTextView.text = fWatchTime.format(date)
            clockAmPm.visibility = View.GONE
        } else {
            val fWatchTime = SimpleDateFormat("hh:mm", Locale.ENGLISH)
            val fWatchTimeAP = SimpleDateFormat("aa", Locale.ENGLISH)
            clockTextView.text = fWatchTime.format(date)
            clockAmPm.text = fWatchTimeAP.format(date)
            clockAmPm.visibility = View.VISIBLE
        }
        val fWatchDate = SimpleDateFormat("EEE, MMM dd", Locale.ENGLISH)
        dateTextView.text = fWatchDate.format(date)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
