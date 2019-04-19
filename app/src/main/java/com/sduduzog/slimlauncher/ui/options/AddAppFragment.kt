package com.sduduzog.slimlauncher.ui.options

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.adapters.AddAppAdapter
import com.sduduzog.slimlauncher.data.MainViewModel
import com.sduduzog.slimlauncher.data.model.App
import com.sduduzog.slimlauncher.utils.BaseFragment
import com.sduduzog.slimlauncher.utils.LoadInstalledApps
import com.sduduzog.slimlauncher.utils.OnAppClickedListener
import kotlinx.android.synthetic.main.add_app_fragment.*

class AddAppFragment : BaseFragment(), OnAppClickedListener {

    override fun getFragmentView(): ViewGroup = add_app_fragment

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_app_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = AddAppAdapter(this)

        add_app_fragment_list.adapter = adapter

        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        } ?: throw Error("How the fuck is this fragment alive while there's no activity?")
        viewModel.installedApps.observe(this, Observer {
            val homeApps = viewModel.apps.value.orEmpty()
            it?.let { apps ->
                adapter.setItems(apps.filterNot { app -> homeApps.map { homeApp -> homeApp.packageName }.contains(app.packageName) })
                add_app_fragment_progress_bar.visibility = View.GONE
            } ?: run {
                add_app_fragment_progress_bar.visibility = View.VISIBLE
            }
        })
        LoadInstalledApps(viewModel).execute(context!!.packageManager)
        add_app_fragment_edit_text.addTextChangedListener(onTextChangeListener)
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

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                LoadInstalledApps(viewModel, s.toString()).execute(context!!.packageManager)
            }
        }
    }

    override fun onAppClicked(app: App) {
        viewModel.add(app)
        Navigation.findNavController(add_app_fragment).popBackStack()
    }

}