package com.sduduzog.slimlauncher.ui.main

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.ui.main.model.HomeApp

class MainAppsAdapter(private var mValues: MutableSet<HomeApp>,
                      private var mContext: Context?,
                      private val mListener: MainFragment.OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<MainAppsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAppsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.main_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainAppsAdapter.ViewHolder, position: Int) {
        val item = mValues.elementAt(position)
        holder.mLabelView.text = item.appName
        holder.mIconView.setImageDrawable(mContext?.packageManager?.getApplicationIcon(item.packageName))
        holder.mLabelView.setOnClickListener {
            mListener?.onLaunch(item)
        }
    }

    override fun getItemCount(): Int = mValues.size
    fun setApps(it: List<HomeApp>) {
        mValues = mutableSetOf()
        mValues.addAll(it)
        notifyDataSetChanged()
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mLabelView: TextView = mView.findViewById(R.id.main_label)
        val mIconView: AppCompatImageView = mView.findViewById(R.id.app_icon)

        override fun toString(): String {
            return super.toString() + " '" + mLabelView.text + "'"
        }
    }
}