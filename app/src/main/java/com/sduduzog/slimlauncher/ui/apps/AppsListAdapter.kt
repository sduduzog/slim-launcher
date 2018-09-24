package com.sduduzog.slimlauncher.ui.apps

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.App


import com.sduduzog.slimlauncher.ui.apps.AppsFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_app.view.*


class AppsListAdapter(
        private var mValues: MutableSet<App>,
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
                .inflate(R.layout.fragment_app, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues.elementAt(position)
        holder.mLabelView.text = item.label
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size
    fun setList(apps: MutableSet<App>) {
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
