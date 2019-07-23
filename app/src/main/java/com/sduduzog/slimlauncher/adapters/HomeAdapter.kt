package com.sduduzog.slimlauncher.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.model.HomeApp
import com.sduduzog.slimlauncher.utils.OnLaunchAppListener


class HomeAdapter(private val listener: OnLaunchAppListener)
    : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private val apps: MutableList<HomeApp> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.main_fragment_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = apps[position]
        holder.mLabelView.text = item.appName
        holder.mLabelView.setOnClickListener {
            listener.onLaunch(item, it)
        }
    }

    override fun getItemCount(): Int = apps.size

    fun setItems(list: List<HomeApp>) {
        apps.addAll(list)
        notifyDataSetChanged()
    }


    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        val mLabelView: TextView = mView.findViewById(R.id.home_fragment_list_item_app_name)

        override fun toString(): String = "${super.toString()} '${mLabelView.text}'"
    }
}