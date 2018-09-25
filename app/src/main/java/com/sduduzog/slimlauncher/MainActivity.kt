package com.sduduzog.slimlauncher

import android.arch.lifecycle.ViewModelProviders
import android.content.*
import android.content.pm.ResolveInfo
import android.content.res.Resources
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import com.sduduzog.slimlauncher.data.App
import com.sduduzog.slimlauncher.ui.main.MainViewModel
import java.util.*


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener, NavController.OnNavigatedListener {

    private lateinit var settings: SharedPreferences
    private val label = "main_fragment"
    private lateinit var currentLabel: String

    private lateinit var viewModel: MainViewModel
    private lateinit var receiver: PackageInfoReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        settings = getSharedPreferences("settings", MODE_PRIVATE)
        settings.registerOnSharedPreferenceChangeListener(this)
        val navigator = findNavController(this, R.id.nav_host_fragment)
        navigator.addOnNavigatedListener(this)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        updateApps()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, s: String?) {
        recreate()
    }

    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()
        settings = getSharedPreferences("settings", MODE_PRIVATE)

        val active = settings.getBoolean("theme", false)
        if (active) {
            theme.applyStyle(R.style.AppDarkTheme, true)
        } else {
            theme.applyStyle(R.style.AppLightTheme, true)
        }
        return theme
    }

    override fun onBackPressed() {
        if (currentLabel != label)
            super.onBackPressed()
    }

    override fun onNavigated(controller: NavController, destination: NavDestination) {
        currentLabel = destination.label.toString()
    }

    private fun updateApps(){
        val apps: MutableList<App> = mutableListOf()
        val pm = packageManager
        val main = Intent(Intent.ACTION_MAIN, null)

        main.addCategory(Intent.CATEGORY_LAUNCHER)

        val launchables = pm.queryIntentActivities(main, 0)
        Collections.sort(launchables,
                ResolveInfo.DisplayNameComparator(pm))
        for (i in launchables.indices) {
            val item = launchables[i]
            val activity = item.activityInfo
            val app = App(activity.loadLabel(pm).toString(), activity.name, activity.applicationInfo.packageName)
            apps.add(app)
        }
        viewModel.bulkInsert(apps)
    }

    override fun onStart() {
        super.onStart()
        receiver = PackageInfoReceiver()
        registerReceiver(receiver, IntentFilter(Intent.ACTION_PACKAGE_ADDED))
        registerReceiver(receiver, IntentFilter(Intent.ACTION_PACKAGE_REMOVED))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

    inner class PackageInfoReceiver: BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent?) {
            updateApps()
        }

    }
}
