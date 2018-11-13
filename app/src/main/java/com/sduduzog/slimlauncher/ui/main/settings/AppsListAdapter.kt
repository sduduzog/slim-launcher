package com.sduduzog.slimlauncher.ui.main.settings

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.ui.main.model.App
import com.sduduzog.slimlauncher.ui.main.model.HomeApp
import com.sduduzog.slimlauncher.ui.main.model.MainViewModel


import com.sduduzog.slimlauncher.ui.main.settings.AppsFragment.OnAppsChooserListener

import kotlinx.android.synthetic.main.apps_list_item.view.*


class AppsListAdapter(fragment: Fragment,
        private val mListener: OnAppsChooserListener?,
                      private val appIndex: Int)
    : RecyclerView.Adapter<AppsListAdapter.ViewHolder>() {

    private var mValues: List<App> = listOf()
    private var viewModel: MainViewModel =  ViewModelProviders.of(fragment).get(MainViewModel::class.java)

    init {
        viewModel.apps.observe(fragment, Observer {
            if (it != null) {
                mValues = it
                notifyDataSetChanged()
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.apps_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues.elementAt(position)
        holder.mLabelView.text = item.appName
        with(holder.mView) {
            tag = item
            setOnClickListener {
                val app = it.tag as App
                val homeApp = HomeApp(app.appName, app.packageName, app.activityName, appIndex)
                viewModel.addToHomeScreen(homeApp)
                mListener?.onAppChosen()
            }
        }
    }

    override fun getItemCount(): Int = mValues.size


    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mLabelView: TextView = mView.label

        override fun toString(): String {
            return super.toString() + " '" + mLabelView.text + "'"
        }
    }
}
