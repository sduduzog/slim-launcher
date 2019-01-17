package com.sduduzog.slimlauncher.ui.main

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.HomeApp


class HomeAppsAdapter(private var fragment: MainFragment)
    : RecyclerView.Adapter<HomeAppsAdapter.ViewHolder>() {

    private var apps: List<HomeApp> = listOf()
    private var viewModel = ViewModelProviders.of(fragment).get(MainViewModel::class.java)

    init {
        viewModel.homeApps.observe(fragment, Observer {
            if (it != null) {
                apps = it
                notifyDataSetChanged()
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAppsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.main_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAppsAdapter.ViewHolder, position: Int) {
        val item = apps.elementAt(position)
        holder.mLabelView.text = item.appName
        holder.mLabelView.setOnClickListener {
            val name = ComponentName(item.packageName, item.activityName)
            val intent = Intent()
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            intent.component = name
            try {
                val left = 0
                val top = 0
                val width = it.measuredWidth
                val height = it.measuredHeight
                val opts = ActivityOptionsCompat.makeClipRevealAnimation(it, left, top, width, height)
                fragment.startActivity(intent, opts.toBundle())
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(fragment.context, "${item.appName} seems to be uninstalled, removing from list", Toast.LENGTH_LONG).show()
                viewModel.deleteApp(item)
            }
        }
    }

    override fun getItemCount(): Int = apps.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mLabelView: TextView = mView.findViewById(R.id.main_label)

        override fun toString(): String {
            return super.toString() + " '" + mLabelView.text + "'"
        }
    }
}