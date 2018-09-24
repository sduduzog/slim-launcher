package com.sduduzog.slimlauncher.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.App

class MainAppsAdapter(private var mValues: MutableSet<App>,
                      private val mListener: MainFragment.OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<MainAppsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAppsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.main_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainAppsAdapter.ViewHolder, position: Int) {
        val item = mValues.elementAt(position)
        holder.mLabelView.text = item.label
        holder.mLabelView.setOnClickListener {
            mListener?.onLaunch(item)
        }
    }

    override fun getItemCount(): Int = mValues.size
    fun setApps(it: List<App>) {
        mValues = mutableSetOf()
        mValues.addAll(it)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mLabelView: TextView = mView.findViewById(R.id.main_label)

        override fun toString(): String {
            return super.toString() + " '" + mLabelView.text + "'"
        }
    }
}