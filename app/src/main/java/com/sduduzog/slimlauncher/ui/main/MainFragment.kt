package com.sduduzog.slimlauncher.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.*
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.HomeApp
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var receiver: BroadcastReceiver
    private lateinit var adapter: MainAppsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        adapter = MainAppsAdapter(mutableSetOf(), InteractionHandler())
        mainAppsList.adapter = adapter
        viewModel.homeApps.observe(this, Observer {
            if (it != null) {
                adapter.setApps(it)
            }
        })
        settingsButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_openSettingsFragment))
        clockTextView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                startActivity(Intent(android.provider.AlarmClock.ACTION_SHOW_ALARMS))
            }
        }
        updateUi()
    }

    override fun onStart() {
        super.onStart()
        receiver = ClockReceiver()
        activity?.registerReceiver(receiver, IntentFilter(Intent.ACTION_TIME_TICK))
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
                Toast.makeText(activity, "${item.appName} seems to be uninstalled, removing from list", Toast.LENGTH_LONG).show()
                viewModel.deleteApp(item)
            }
        }
    }

    interface OnListFragmentInteractionListener {
        fun onLaunch(item: HomeApp)
    }
}
