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
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.MainActivity
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.ui.BaseFragment
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : BaseFragment(), MainActivity.OnBackPressedListener {

    private lateinit var receiver: BroadcastReceiver

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        home_fragment_list.adapter = HomeAppsAdapter(this)
        setEventListeners()

        home_fragment_options.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_optionsFragment))
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


        home_fragment_time.setOnClickListener {
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

        home_fragment_date.setOnClickListener {
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

        home_fragment_call.setOnClickListener {
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

        home_fragment_camera.setOnClickListener {
            try {
                val intent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
                launchActivity(it, intent)
            } catch (e: Exception) {
                // Do nothing
            }
        }
    }

    fun updateUi() {
        val twenty4Hour = context?.getSharedPreferences(getString(R.string.prefs_settings), Context.MODE_PRIVATE)
                ?.getBoolean(getString(R.string.prefs_settings_key_time_format), true)
        val date = Date()
        if (twenty4Hour as Boolean) {
            val fWatchTime = SimpleDateFormat("h:mm", Locale.ENGLISH)
            val fWatchTimeAP = SimpleDateFormat("aa", Locale.ENGLISH)
            home_fragment_time.text = fWatchTime.format(date)
            home_fragment_time_format.text = fWatchTimeAP.format(date)
            home_fragment_time_format.visibility = View.VISIBLE
        } else {
            val fWatchTime = SimpleDateFormat("H:mm", Locale.ENGLISH)
            home_fragment_time.text = fWatchTime.format(date)
            home_fragment_time_format.visibility = View.GONE
        }
        val fWatchDate = SimpleDateFormat("EEE, MMM dd", Locale.ENGLISH)
        home_fragment_date.text = fWatchDate.format(date)
    }


    inner class ClockReceiver : BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent?) {
            updateUi()
        }
    }
}
