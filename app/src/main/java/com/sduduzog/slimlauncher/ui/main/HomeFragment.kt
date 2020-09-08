package com.sduduzog.slimlauncher.ui.main


import android.content.*
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.adapters.HomeAdapter
import com.sduduzog.slimlauncher.models.HomeApp
import com.sduduzog.slimlauncher.models.MainViewModel
import com.sduduzog.slimlauncher.utils.BaseFragment
import com.sduduzog.slimlauncher.utils.OnLaunchAppListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment(), OnLaunchAppListener {
    private lateinit var settings : SharedPreferences

    private lateinit var receiver: BroadcastReceiver
    private val viewModel: MainViewModel by viewModels()

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

        settings = PreferenceManager.getDefaultSharedPreferences(context)
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
        val timeIsShortcut  = settings.getBoolean(getString(R.string.prefs_settings_key_shortcut_time), false)
        val dateIsShortcut = settings.getBoolean(getString(R.string.prefs_settings_key_shortcut_date), false)

        if (timeIsShortcut) {
            home_fragment_time.setOnClickListener {
                try {
                    val intent = Intent(AlarmClock.ACTION_SHOW_ALARMS)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    launchActivity(it, intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                    // Do nothing, we've failed :(
                }
            }
        }

        if (dateIsShortcut) {
            home_fragment_date.setOnClickListener {
                try {
                    val builder = CalendarContract.CONTENT_URI.buildUpon().appendPath("time")
                    val intent = Intent(Intent.ACTION_VIEW, builder.build())
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
        val active = Integer.parseInt(settings.getString(getString(R.string.prefs_settings_key_time_format), "0")!!)
        val date = Date()

        val fWatchTime = when(active) {
            1 -> SimpleDateFormat("H:mm", Locale.ROOT)
            2 -> SimpleDateFormat("h:mm aa", Locale.ROOT)
            else -> DateFormat.getTimeInstance(DateFormat.SHORT)
        }
        home_fragment_time.text = fWatchTime.format(date)


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
            intent.resolveActivity(requireActivity().packageManager)?.let {
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

    private fun setViewVisibility(){
        setVisibility(home_fragment_time, R.string.prefs_settings_key_toggle_time)
        setVisibility(home_fragment_date, R.string.prefs_settings_key_toggle_date)
        setVisibility(home_fragment_call, R.string.prefs_settings_key_toggle_call)
        setVisibility(home_fragment_camera, R.string.prefs_settings_key_toggle_camera)
    }

    private fun setVisibility(view : View, settingRef : Int){
        val showView = settings.getBoolean(getString(settingRef), true)
        view.visibility = if (showView) View.VISIBLE else View.INVISIBLE
    }
}
