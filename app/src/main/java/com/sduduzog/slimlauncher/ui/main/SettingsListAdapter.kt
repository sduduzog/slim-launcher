package com.sduduzog.slimlauncher.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.App

class SettingsListAdapter(private var apps: List<App>, private val listener: SettingsFragment.OnListFragmentInteractionListener) : RecyclerView.Adapter<SettingsListAdapter.AppViewHolder>() {

    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.settings_apps_list_item, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.labelText.text = apps[position].label
        holder.removeButton.setOnClickListener {
            val packageName = apps[position].packageName
                listener.onRemove(packageName)
        }
    }

    override fun getItemCount(): Int {
        return apps.size
    }

    internal fun setApps(apps: List<App>) {
        this.apps = apps
        notifyDataSetChanged()
    }

    inner class AppViewHolder(view: View)// Bind item views here
        : RecyclerView.ViewHolder(view) {
        val removeButton: Button = view.findViewById(R.id.settings_item_removeButton)
        val labelText: TextView = view.findViewById(R.id.settings_list_item_textView)
    }
}
