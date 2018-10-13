package com.sduduzog.slimlauncher.ui.main

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.ui.main.model.App


import com.sduduzog.slimlauncher.ui.main.AppsFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.apps_list_item.view.*


class AppsListAdapter(
        private var mValues: List<App>,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<AppsListAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as App
            mListener?.onListFragmentInteraction(item)
        }
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
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size
    fun setList(apps: List<App>) {
        mValues = apps
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mLabelView: TextView = mView.label

        override fun toString(): String {
            return super.toString() + " '" + mLabelView.text + "'"
        }
    }
}
