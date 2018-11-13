package com.sduduzog.slimlauncher.ui.main.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.ui.main.model.HomeApp
import com.sduduzog.slimlauncher.ui.main.model.MainViewModel

class SettingsListAdapter(private val fragment: Fragment) : RecyclerView.Adapter<SettingsListAdapter.AppViewHolder>() {

    private lateinit var inflater: LayoutInflater
    private var apps: List<HomeApp> = listOf()
    private var viewModel: MainViewModel = ViewModelProviders.of(fragment).get(MainViewModel::class.java)

    init {
        viewModel.homeApps.observe(fragment, Observer {
            if (it != null) {
                apps = it
                notifyDataSetChanged()
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.settings_list_item, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {

        val app = apps.singleOrNull {
            it.sortingIndex == position
        }

        if (app != null) {
            with(app) {
                holder.itemText.text = this.appName
            }
            holder.itemSubtext.visibility = View.VISIBLE
            holder.itemButton.visibility = View.GONE
        } else {
            holder.itemText.text = fragment.getString(R.string.settings_list_item_text, getNthString(position))
            holder.itemSubtext.visibility = View.INVISIBLE
            holder.itemButton.visibility = View.VISIBLE
            val bundle = Bundle()
            bundle.putInt("index", position)
            holder.itemButton.setOnClickListener(
                    Navigation.createNavigateOnClickListener(R.id.action_openAppsFragment, bundle))
        }
    }

    override fun getItemCount() = if (apps.size != 5) apps.size + 1 else apps.size

    private fun getNthString(index: Int): String {
        val i = index + 1
        return when (i) {
            1 -> "${i}st"
            2 -> "${i}nd"
            3 -> "${i}rd"
            else -> "${i}th"
        }
    }

    inner class AppViewHolder(view: View)// Bind item views here
        : RecyclerView.ViewHolder(view) {
        val itemText: TextView = view.findViewById(R.id.item_text)
        val itemSubtext: TextView = view.findViewById(R.id.item_subtext)
        val itemButton: Button = view.findViewById(R.id.item_button)
    }
}
