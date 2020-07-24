package com.sduduzog.slimlauncher.ui.main

import android.content.*
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.adapters.HomeAdapter
import com.sduduzog.slimlauncher.models.HomeApp
import com.sduduzog.slimlauncher.models.MainViewModel
import com.sduduzog.slimlauncher.utils.BaseFragment
import com.sduduzog.slimlauncher.utils.OnLaunchAppListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class HomeFragment : BaseFragment(), SharedPreferences.OnSharedPreferenceChangeListener, OnLaunchAppListener {
    private lateinit var settings : SharedPreferences

    private lateinit var timeView : TextView
    private lateinit var dateView : TextView
    private lateinit var callView : ImageView
    private lateinit var cameraView : ImageView

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var receiver: BroadcastReceiver
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter1 = HomeAdapter(this)
        val adapter2 = HomeAdapter(this)
        home_fragment_list.adapter = adapter1
        home_fragment_list_exp.adapter = adapter2

        settings = this.context?.getSharedPreferences(getString(R.string.prefs_settings), AppCompatActivity.MODE_PRIVATE)!!
        settings.registerOnSharedPreferenceChangeListener(this)

        activity?.let {
            viewModel = ViewModelProvider(it, viewModelFactory).get(MainViewModel::class.java)
        } ?: throw Error("Activity null, something here is fucked up")

        viewModel.apps.observe(viewLifecycleOwner, Observer { list ->
            list?.let { apps ->
                adapter1.setItems(apps.filter {
                    it.sortingIndex < 4
                })
                adapter2.setItems(apps.filter {
                    it.sortingIndex >= 4
                })
            }
        })

        setEventListeners()
        home_fragment_options.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_optionsFragment))

        // Set hideable views
        timeView = home_fragment_time
        dateView = home_fragment_date
        callView = home_fragment_call
        cameraView = home_fragment_camera
    }

    override fun onStart() {
        super.onStart()
        setViewVisibility()
        receiver = ClockReceiver()
        activity?.registerReceiver(receiver, IntentFilter(Intent.ACTION_TIME_TICK))
    }

    override fun getFragmentView(): ViewGroup = home_fragment

    override fun onResume() {
        super.onResume()
        updateClock()
    }

    override fun onStop() {
        super.onStop()
        activity?.unregisterReceiver(receiver)
    }

    private fun setEventListeners() {
        val timeIsShortcut  = settings.getBoolean(getString(R.string.prefs_settings_key_shortcut_time), true)
        val dateIsShortcut = settings.getBoolean(getString(R.string.prefs_settings_key_shortcut_date), true)

        if (timeIsShortcut) {
            home_fragment_time.setOnClickListener { view ->
                try {
                    val pm = context?.packageManager!!
                    val intent = Intent(AlarmClock.ACTION_SHOW_ALARMS)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    val componentName = intent.resolveActivity(pm)
                    if (componentName == null) launchActivity(view, intent) else
                        pm.getLaunchIntentForPackage(componentName.packageName)?.let {
                            launchActivity(view, it)
                        }
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                    // Do nothing, we've failed :(
                }
            }
        }

        if (dateIsShortcut) {
            home_fragment_date.setOnClickListener {
                try {
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_APP_CALENDAR)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    launchActivity(it, intent)
                } catch (e: ActivityNotFoundException) {
                    // Do nothing, we've failed :(
                }
            }
        }

        home_fragment_call.setOnClickListener { view ->
            try {
                val pm = context?.packageManager!!
                val intent = Intent(Intent.ACTION_DIAL)
                val componentName = intent.resolveActivity(pm)
                if (componentName == null) launchActivity(view, intent) else
                    pm.getLaunchIntentForPackage(componentName.packageName)?.let {
                        launchActivity(view, it)
                    } ?: run { launchActivity(view, intent) }
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

    fun updateClock() {
        val twenty4Hour = context?.getSharedPreferences(getString(R.string.prefs_settings), Context.MODE_PRIVATE)
                ?.getBoolean(getString(R.string.prefs_settings_key_time_format), true)
        val date = Date()
        if (twenty4Hour as Boolean) {
            val fWatchTime = SimpleDateFormat("h:mm aa", Locale.ROOT)
            home_fragment_time.text = fWatchTime.format(date)
        } else {
            val fWatchTime = SimpleDateFormat("H:mm", Locale.ROOT)
            home_fragment_time.text = fWatchTime.format(date)
        }
        val fWatchDate = SimpleDateFormat("EEE, MMM dd", Locale.ROOT)
        home_fragment_date.text = fWatchDate.format(date)
    }

    override fun onLaunch(app: HomeApp, view: View) {
        try {
            val intent = Intent()
            val name = ComponentName(app.packageName, app.activityName)
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            intent.component = name
            intent.resolveActivity(activity!!.packageManager)?.let {
                launchActivity(view, intent)
            }
        } catch (e: Exception) {
            // Do no shit yet
        }
    }

    override fun onBack(): Boolean {
        home_fragment.transitionToStart()
        return true
    }

    override fun onHome() {
        home_fragment.transitionToEnd()
    }

    inner class ClockReceiver : BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent?) {
            updateClock()
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        setViewVisibility()
    }

    private fun setViewVisibility(){
        val showTime = settings.getBoolean(getString(R.string.prefs_settings_key_toggle_time), true)
        val showDate = settings.getBoolean(getString(R.string.prefs_settings_key_toggle_date), true)
        val showCall = settings.getBoolean(getString(R.string.prefs_settings_key_toggle_call), true)
        val showCamera = settings.getBoolean(getString(R.string.prefs_settings_key_toggle_camera), true)

        timeView.visibility = setVisibility(showTime)
        dateView.visibility = setVisibility(showDate)
        callView.visibility = setVisibility(showCall)
        cameraView.visibility = setVisibility(showCamera)
    }

    private fun setVisibility(show : Boolean) : Int{
        return if(show) View.VISIBLE else View.INVISIBLE
    }
}
