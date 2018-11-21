package com.sduduzog.slimlauncher.ui.main

import android.animation.ObjectAnimator
import android.content.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sduduzog.slimlauncher.MainActivity
import com.sduduzog.slimlauncher.R
import kotlinx.android.synthetic.main.main_bottom_sheet.*
import kotlinx.android.synthetic.main.main_content.*
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment() {

    @Suppress("PropertyName")
    val TAG: String = "MainFragment"

    private lateinit var adapter: HomeAppsAdapter
    private lateinit var receiver: BroadcastReceiver
    private lateinit var sheetBehavior: BottomSheetBehavior<FrameLayout>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated")
        adapter = HomeAppsAdapter(this)
        mainAppsList.adapter = adapter

        sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        optionsView.alpha = 0.0f
        setEventListeners()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
        receiver = ClockReceiver()
        activity?.registerReceiver(receiver, IntentFilter(Intent.ACTION_TIME_TICK))

        doBounceAnimation(ivExpand)
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }


    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        updateUi()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        with(context as MainActivity) {
            this.onBackPressedListener = object : MainActivity.OnBackPressedListener {
                override fun onBackPressed() {
                    sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        activity?.unregisterReceiver(receiver)
    }

    private fun setEventListeners() {
        bottomSheet.setOnClickListener {

        }
        clockTextView.setOnClickListener {
            try{
                val intent = Intent(AlarmClock.ACTION_SHOW_ALARMS)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } finally {
                // Do nothing
            }
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
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    iconTray.visibility = View.VISIBLE
                }
            }
        })
        settingsText.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_openSettingsFragment))
        deviceSettingsText.setOnClickListener { openSettings() }
        rateAppText.setOnClickListener { rateApp() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            changeLauncherText.setOnClickListener {
                startActivity(Intent(Settings.ACTION_HOME_SETTINGS))
            }
        } else changeLauncherText.visibility = View.GONE
        aboutText.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_openAboutFragment))
        notesText.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_openNotesListFragment))
        ivCall.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_DIAL)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e(TAG, e.message)
            }
        }
        ivCamera.setOnClickListener {
            try {
                val intent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e(TAG, e.message)
            }
        }

        ivExpand.setOnClickListener {
            if (sheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) sheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }
    }

    private fun rateApp() {
        val uri = Uri.parse("market://details?id=" + context?.packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
        }
        try {
            startActivity(goToMarket)
            Log.d(TAG, goToMarket.data?.query)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context?.packageName)))
        }
    }

    private fun openSettings() {
        startActivity(Intent(android.provider.Settings.ACTION_SETTINGS))
    }

    private fun doBounceAnimation(targetView: View) {
        val animator = ObjectAnimator.ofFloat(targetView, "translationY", 0f, -20f, 0f)
        animator.interpolator = EasingInterpolator(Ease.QUINT_OUT)
        animator.startDelay = 1000
        animator.duration = 1000
        animator.repeatCount = 1
        animator.start()
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
