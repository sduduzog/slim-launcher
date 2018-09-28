package com.sduduzog.slimlauncher.ui.main

import android.animation.ObjectAnimator
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED
import android.support.design.widget.BottomSheetBehavior.STATE_HALF_EXPANDED
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator
import com.sduduzog.slimlauncher.MainViewModel
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.HomeApp
import kotlinx.android.synthetic.main.main_bottom_sheet.*
import kotlinx.android.synthetic.main.main_content.*
import java.text.SimpleDateFormat
import java.util.*


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var receiver: BroadcastReceiver
    private lateinit var adapter: MainAppsAdapter
    private lateinit var sheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var themeChooser: ThemeChooserDialog

    @Suppress("PropertyName")
    val TAG: String = "MainFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheet.alpha = 0.0f
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        adapter = MainAppsAdapter(mutableSetOf(), InteractionHandler())
        themeChooser = ThemeChooserDialog.getThemeChooser()
        mainAppsList.adapter = adapter
        viewModel.homeApps.observe(this, Observer {
            if (it != null) {
                adapter.setApps(it)
            }
        })
        setEventListeners()
    }

    override fun onStart() {
        super.onStart()
        receiver = ClockReceiver()
        activity?.registerReceiver(receiver, IntentFilter(Intent.ACTION_TIME_TICK))
        doBounceAnimation(ivExpand)
        sheetBehavior.state = STATE_COLLAPSED
    }

    override fun onResume() {
        super.onResume()
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
        val fWatchTime = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val fWatchDate = SimpleDateFormat("EEE, MMM dd", Locale.ENGLISH)
        val date = Date()
        clockTextView.text = fWatchTime.format(date)
        dateTextView.text = fWatchDate.format(date)
        doBounceAnimation(ivExpand)
    }

    inner class InteractionHandler : OnListFragmentInteractionListener {
        override fun onLaunch(item: HomeApp) {
            val name = ComponentName(item.packageName, item.activityName)
            val intent = Intent()
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            intent.component = name
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.e(TAG, e.message)
                Toast.makeText(activity, "${item.appName} seems to be uninstalled, removing from list", Toast.LENGTH_LONG).show()
                viewModel.deleteApp(item)
            }
        }
    }

    interface OnListFragmentInteractionListener {
        fun onLaunch(item: HomeApp)
    }

    private fun doBounceAnimation(targetView: View) {
        val animator = ObjectAnimator.ofFloat(targetView, "translationY", 0f, -20f, 0f)
        animator.interpolator = EasingInterpolator(Ease.QUINT_OUT)
        animator.startDelay = 1000
        animator.duration = 1000
        animator.repeatCount = 1
        animator.start()
    }

    private fun rateApp() {
        val uri = Uri.parse("market://details?id=" + context!!.packageName)
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
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context!!.packageName)))
        }
    }

    private fun openSettings() {
        startActivity(Intent(android.provider.Settings.ACTION_SETTINGS))
    }

    private fun changeTheme() {
        themeChooser.show(fragmentManager, TAG)
    }

    private fun setEventListeners(){
        clockTextView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    val intent = Intent(android.provider.AlarmClock.ACTION_SHOW_ALARMS)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e(TAG, e.message)
                }
            }
        }
        bottomSheet.setOnClickListener {
            if (sheetBehavior.state == STATE_COLLAPSED) sheetBehavior.state = STATE_HALF_EXPANDED
        }
        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
                val alpha = 3 * p1
                bottomSheet.alpha = alpha
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                ivExpand.visibility = View.INVISIBLE
                if (newState == STATE_COLLAPSED) {
                    ivExpand.visibility = View.VISIBLE
                }
            }
        })
        changeThemeText.setOnClickListener { changeTheme() }
        settingsText.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_openSettingsFragment))
        deviceSettingsText.setOnClickListener { openSettings() }
        rateAppText.setOnClickListener { rateApp() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            changeLauncherText.setOnClickListener {
                startActivity(Intent(android.provider.Settings.ACTION_HOME_SETTINGS))
            }
        } else changeLauncherText.visibility = View.INVISIBLE
        aboutText.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_openAboutFragment))
    }
}
