package com.sduduzog.slimlauncher.ui.main

import android.content.*
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import com.sduduzog.slimlauncher.MainActivity
import com.sduduzog.slimlauncher.R
import kotlinx.android.synthetic.main.main_fragment2.*
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : StatusBarThemeFragment(), MainActivity.OnBackPressedListener {

    private lateinit var receiver: BroadcastReceiver

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        main_fragment_list.adapter = HomeAppsAdapter(this)
        setEventListeners()
    }

    override fun onStart() {
        super.onStart()
        receiver = ClockReceiver()
        activity?.registerReceiver(receiver, IntentFilter(Intent.ACTION_TIME_TICK))
    }

    override fun getFragmentView(): View {
        return main_fragment2
    }

    override fun onResume() {
        super.onResume()
        updateUi()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        with(context as MainActivity) {
            this.onBackPressedListener = this@MainFragment
        }
    }

    override fun onDetach() {
        super.onDetach()
        with(context as MainActivity) {
            this.onBackPressedListener = null
        }
    }

    override fun onStop() {
        super.onStop()
        activity?.unregisterReceiver(receiver)
    }

    override fun onBackPress() {
    }

    override fun onBackPressed() {
        // Do nothing
    }

    private fun setEventListeners() {


        main_fragment_time.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    val intent = Intent(AlarmClock.ACTION_SHOW_ALARMS)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    val left = 0
                    val top = 0
                    val width = it.measuredWidth
                    val height = it.measuredHeight
                    val opts = ActivityOptionsCompat.makeClipRevealAnimation(it, left, top, width, height)
                    if (intent.resolveActivity(context!!.packageManager) != null)
                        startActivity(intent, opts.toBundle())
                } catch (e: ActivityNotFoundException) {
                    // Do nothing, we've failed :(
                }
            }
        }

        main_fragment_date.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_APP_CALENDAR)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                val left = 0
                val top = 0
                val width = it.measuredWidth
                val height = it.measuredHeight
                val opts = ActivityOptionsCompat.makeClipRevealAnimation(it, left, top, width, height)
                startActivity(intent, opts.toBundle())
            } catch (e: ActivityNotFoundException) {
                // Do nothing, we've failed :(
            }
        }

        main_fragment_call.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_DIAL)
                val left = 0
                val top = 0
                val width = it.measuredWidth
                val height = it.measuredHeight
                val opts = ActivityOptionsCompat.makeClipRevealAnimation(it, left, top, width, height)
                startActivity(intent, opts.toBundle())
            } catch (e: Exception) {
                // Do nothing
            }
        }

        main_fragment_camera.setOnClickListener {
            try {
                val intent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
                val left = 0
                val top = 0
                val width = it.measuredWidth
                val height = it.measuredHeight
                val opts = ActivityOptionsCompat.makeClipRevealAnimation(it, left, top, width, height)
                startActivity(intent, opts.toBundle())
            } catch (e: Exception) {
                // Do nothing
            }
        }
    }

    fun updateUi() {
        val twenty4Hour = context?.getSharedPreferences(getString(R.string.prefs_settings), Context.MODE_PRIVATE)
                ?.getBoolean(getString(R.string.prefs_settings_key_clock_type), false)
        val date = Date()
        if (twenty4Hour as Boolean) {
            val fWatchTime = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            main_fragment_time.text = fWatchTime.format(date)
            main_fragment_time_format.visibility = View.GONE
        } else {
            val fWatchTime = SimpleDateFormat("hh:mm", Locale.ENGLISH)
            val fWatchTimeAP = SimpleDateFormat("aa", Locale.ENGLISH)
            main_fragment_time.text = fWatchTime.format(date)
            main_fragment_time_format.text = fWatchTimeAP.format(date)
            main_fragment_time_format.visibility = View.VISIBLE
        }
        val fWatchDate = SimpleDateFormat("EEE, MMM dd", Locale.ENGLISH)
        main_fragment_date.text = fWatchDate.format(date)
    }


    inner class ClockReceiver : BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent?) {
            updateUi()
        }
    }
}
