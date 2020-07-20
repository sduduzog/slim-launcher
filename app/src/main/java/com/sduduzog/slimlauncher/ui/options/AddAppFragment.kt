package com.sduduzog.slimlauncher.ui.options

import android.content.Context
import android.content.Intent
import android.content.pm.LauncherApps
import android.content.pm.ResolveInfo
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.os.UserManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.BuildConfig
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.adapters.AddAppAdapter
import com.sduduzog.slimlauncher.data.model.App
import com.sduduzog.slimlauncher.models.AddAppViewModel
import com.sduduzog.slimlauncher.utils.BaseFragment
import com.sduduzog.slimlauncher.utils.OnAppClickedListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.add_app_fragment.*
import java.util.*
import javax.inject.Inject

class AddAppFragment : BaseFragment(), OnAppClickedListener {

    override fun getFragmentView(): ViewGroup = add_app_fragment

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AddAppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_app_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = AddAppAdapter(this)

        add_app_fragment_list.adapter = adapter

        activity?.let {
            viewModel = ViewModelProvider(it, viewModelFactory).get(AddAppViewModel::class.java)
        } ?: throw Error("How the fuck is this fragment alive while there's no activity?")
        viewModel.apps.observe(viewLifecycleOwner, Observer {
            it?.let { apps ->
                adapter.setItems(apps)
                add_app_fragment_progress_bar.visibility = View.GONE
            } ?: run {
                add_app_fragment_progress_bar.visibility = View.VISIBLE
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.setInstalledApps(getInstalledApps())
        viewModel.filterApps("")
        add_app_fragment_edit_text.addTextChangedListener(onTextChangeListener)
    }

    override fun onPause() {
        super.onPause()
        add_app_fragment_edit_text?.removeTextChangedListener(onTextChangeListener)
    }

    private val onTextChangeListener: TextWatcher = object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
            // Do nothing
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Do nothing
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            viewModel.filterApps(s.toString())
        }
    }

    override fun onAppClicked(app: App) {
        viewModel.addAppToHomeScreen(app)
        Navigation.findNavController(add_app_fragment).popBackStack()
    }

    private fun getInstalledApps(): List<App> {
        val pm = activity!!.packageManager
        val list = mutableListOf<App>()

        val manager = context!!.getSystemService(Context.USER_SERVICE) as UserManager
        val launcher = context!!.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
        val user = Process.myUserHandle()

        for (profile in manager.userProfiles) {
            val prefix = if (profile.equals(user)) "" else "[W] "
            for (activityInfo in launcher.getActivityList(null, profile)) {
                val appInfo = activityInfo.applicationInfo
                val app = App(
                        appName = prefix + activityInfo.label.toString(),
                        packageName = appInfo.packageName,
                        activityName = activityInfo.name,
                        userSerial = manager.getSerialNumberForUser(profile).toInt())
                list.add(app)
            }
        }

        val filter = mutableListOf<String>()
        filter.add(BuildConfig.APPLICATION_ID)
        return list.filterNot { filter.contains(it.packageName) }
    }
}