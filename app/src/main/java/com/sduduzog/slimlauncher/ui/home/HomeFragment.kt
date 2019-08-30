package com.sduduzog.slimlauncher.ui.home

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
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
import com.sduduzog.slimlauncher.utils.InjectableFragment
import com.sduduzog.slimlauncher.utils.OnLaunchAppListener
import kotlinx.android.synthetic.main.home_fragment.*
import javax.inject.Inject


class HomeFragment : InjectableFragment(), OnLaunchAppListener {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: HomeViewModel

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
        viewModel.time.observe(this, Observer { home_fragment_time.text = it; })
        viewModel.date.observe(this, Observer { home_fragment_date.text = it })
        viewModel.firstAdapterApps.observe(this, Observer {
            it.let { apps -> (home_fragment_list.adapter as HomeAdapter).setItems(apps) }
        })
        viewModel.secondAdapterApps.observe(this, Observer {
            it.let { apps -> (home_fragment_list_exp.adapter as HomeAdapter).setItems(apps) }
        })
    }

    override fun getFragmentView(): ViewGroup = home_fragment

    private fun setEventListeners() {

        home_fragment_time.setOnClickListener { view ->
            try {
                val pm = context?.packageManager ?: return@setOnClickListener
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

    }

    override fun onLaunch(app: HomeApp, view: View) {
        try {
            val intent = Intent()
            val name = ComponentName(app.packageName, app.activityName)
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            intent.component = name
            intent.resolveActivity((activity ?: return).packageManager)?.let {
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
}
