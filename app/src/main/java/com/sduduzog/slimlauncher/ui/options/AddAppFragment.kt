package com.sduduzog.slimlauncher.ui.options

import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.BuildConfig
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.adapters.AddAppAdapter
import com.sduduzog.slimlauncher.data.model.App
import com.sduduzog.slimlauncher.models.AddAppViewModel
import com.sduduzog.slimlauncher.utils.BaseFragment
import com.sduduzog.slimlauncher.utils.OnAppClickedListener
import kotlinx.android.synthetic.main.add_app_fragment.*
import java.util.*

class AddAppFragment : BaseFragment(), OnAppClickedListener {

    override fun getFragmentView(): ViewGroup = add_app_fragment

    private lateinit var viewModel: AddAppViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_app_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = AddAppAdapter(this)

        add_app_fragment_list.adapter = adapter

        activity?.let {
            viewModel = ViewModelProviders.of(it).get(AddAppViewModel::class.java)
        } ?: throw Error("How the fuck is this fragment alive while there's no activity?")
        viewModel.apps.observe(this, Observer {
            it?.let { apps ->
                adapter.setItems(apps)
                add_app_fragment_progress_bar.visibility = View.GONE
            } ?: run {
                add_app_fragment_progress_bar.visibility = View.VISIBLE
            }
        })
        add_app_fragment_edit_text.addTextChangedListener(onTextChangeListener)
    }

    override fun onResume() {
        super.onResume()
        viewModel.setInstalledApps(getInstalledApps())
    }

    override fun onDestroy() {
        super.onDestroy()
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
        val main = Intent(Intent.ACTION_MAIN, null)
        main.addCategory(Intent.CATEGORY_LAUNCHER)
        val activitiesList = pm.queryIntentActivities(main, 0)
        Collections.sort(activitiesList, ResolveInfo.DisplayNameComparator(pm))
        activitiesList.indices.forEach {
            val item = activitiesList[it]
            val activity = item.activityInfo
            val app = App(
                    activitiesList[it].loadLabel(pm).toString(),
                    activity.applicationInfo.packageName, activity.name
            )
            list.add(app)
        }
        val filter = mutableListOf<String>()
        filter.add(BuildConfig.APPLICATION_ID)
        return list.filterNot { filter.contains(it.packageName) }
    }
}