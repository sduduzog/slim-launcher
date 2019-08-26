package com.sduduzog.slimlauncher.ui.home

import android.content.*
import android.os.Bundle
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.adapters.HomeAdapter
import com.sduduzog.slimlauncher.data.entity.HomeApp
import com.sduduzog.slimlauncher.utils.BaseFragment
import com.sduduzog.slimlauncher.utils.OnLaunchAppListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.home_fragment.*
import javax.inject.Inject


class HomeFragment : BaseFragment(), OnLaunchAppListener {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var receiver: BroadcastReceiver
    private lateinit var viewModel: HomeViewModel

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
        home_fragment_list.adapter = HomeAdapter(this)
        home_fragment_list_exp.adapter = HomeAdapter(this)
        setupViewModel()
        setEventListeners()
        home_fragment_options
                .setOnClickListener(Navigation
                        .createNavigateOnClickListener(R.id.action_homeFragment_to_optionsFragment))
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        viewModel.time.observe(this, Observer { home_fragment_time.text = it })
        viewModel.date.observe(this, Observer { home_fragment_date.text = it })
        viewModel.firstAdapterApps.observe(this, Observer {
            it.let { apps -> (home_fragment_list.adapter as HomeAdapter).setItems(apps) }
        })
        viewModel.secondAdapterApps.observe(this, Observer {
            it.let { apps -> (home_fragment_list_exp.adapter as HomeAdapter).setItems(apps) }
        })
    }


    override fun onStart() {
        super.onStart()
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

//        home_fragment_call.setOnClickListener { view ->
//            try {
//                val pm = context?.packageManager!!
//                val intent = Intent(Intent.ACTION_DIAL)
//                val componentName = intent.resolveActivity(pm)
//                if (componentName == null) launchActivity(view, intent) else
//                    pm.getLaunchIntentForPackage(componentName.packageName)?.let {
//                        launchActivity(view, it)
//                    } ?: run { launchActivity(view, intent) }
//            } catch (e: Exception) {
//                // Do nothing
//            }
//        }
//
//        home_fragment_camera.setOnClickListener {
//            try {
//                val intent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
//                launchActivity(it, intent)
//            } catch (e: Exception) {
//                // Do nothing
//            }
//        }
    }

    internal fun updateClock() {
        val twenty4Hour = context?.getSharedPreferences(getString(R.string.prefs_settings), Context.MODE_PRIVATE)
                ?.getBoolean(getString(R.string.prefs_settings_key_time_format), true) ?: true
        viewModel.clockTick(twenty4Hour)
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
}
