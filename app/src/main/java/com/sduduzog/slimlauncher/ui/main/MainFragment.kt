package com.sduduzog.slimlauncher.ui.main

import android.content.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.sduduzog.slimlauncher.MainActivity
import com.sduduzog.slimlauncher.R
import kotlinx.android.synthetic.main.main_bottom_sheet.*
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : StatusBarThemeFragment(), MainActivity.OnBackPressedListener {

    private lateinit var receiver: BroadcastReceiver
    private lateinit var sheetBehavior: BottomSheetBehavior<FrameLayout>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainAppsList.adapter = HomeAppsAdapter(this)
        sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        optionsView.alpha = 0.0f
        setEventListeners()
        setupBottomSheet()
    }

    override fun onStart() {
        super.onStart()
        receiver = ClockReceiver()
        activity?.registerReceiver(receiver, IntentFilter(Intent.ACTION_TIME_TICK))
        sheetBehavior.state = STATE_COLLAPSED
    }

    override fun getFragmentView(): View {
        return main
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
        sheetBehavior.state = STATE_COLLAPSED
    }

    override fun onBackPressed() {
        // Do nothing
    }

    private fun setEventListeners() {


        clockTextView.setOnClickListener {
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

        dateTextView.setOnClickListener {
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

        ivCall.setOnClickListener {
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
        ivCall.setOnLongClickListener {
            try {
                val intent = Intent(Intent.ACTION_DIAL, null)
                val left = 0
                val top = 0
                val width = it.measuredWidth
                val height = it.measuredHeight
                val opts = ActivityOptionsCompat.makeClipRevealAnimation(it, left, top, width, height)
                startActivity(intent, opts.toBundle())
            } catch (e: ActivityNotFoundException) {
                // Do nothing
            }
            true
        }

        ivCamera.setOnClickListener {
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

    private fun setupBottomSheet() {
        bottomSheet.setOnClickListener {
            // Do nothing
        }
        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
                val multi = 3 * p1
                optionsView.alpha = multi
                optionsView.cardElevation = p1 * 8
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    optionsView.elevation = p1 * 8
                }
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                iconTray.visibility = View.GONE
                if (newState == STATE_COLLAPSED) {
                    iconTray.visibility = View.VISIBLE
                }
            }
        })
        textView12.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_notesListFragment))
        settingsText.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_settingsFragment))

        rateAppText.setOnClickListener {
            val uri = Uri.parse("market://details?id=" + context?.packageName)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
            }
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + context?.packageName)))
            }
        }
        aboutText.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_aboutFragment))

        deviceSettingsText.setOnClickListener {
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }
        changeLauncherText.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(Intent(Settings.ACTION_HOME_SETTINGS))
            }
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


    inner class ClockReceiver : BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent?) {
            updateUi()
        }
    }
}
