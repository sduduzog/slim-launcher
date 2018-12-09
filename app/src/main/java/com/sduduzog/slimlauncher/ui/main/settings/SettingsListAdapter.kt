package com.sduduzog.slimlauncher.ui.main.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.HomeApp
import com.sduduzog.slimlauncher.ui.main.MainViewModel
import com.sduduzog.slimlauncher.ui.main.OnItemActionListener

class SettingsListAdapter(private val fragment: Fragment) : RecyclerView.Adapter<SettingsListAdapter.AppViewHolder>(), OnItemActionListener {

    private var deletedFromIndex = 0
    private var renamedOnIndex = -1
    private lateinit var inflater: LayoutInflater
    private var displayedApps: ArrayList<HomeApp> = arrayListOf()
    private var viewModel: MainViewModel = ViewModelProviders.of(fragment).get(MainViewModel::class.java)

    private lateinit var touchHelper: ItemTouchHelper

    init {
        viewModel.homeApps.observe(fragment, Observer {
            updateApps(it.orEmpty())
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.settings_list_item, parent, false)
        return AppViewHolder(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        if (position < displayedApps.size) {
            val app = displayedApps[position]
            with(app) {
                holder.itemText.text = this.appName
            }
            holder.itemButton.visibility = View.GONE
            holder.itemDragger.visibility = View.VISIBLE
            holder.itemDragger.setOnTouchListener { _, motionEvent ->
                if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                    touchHelper.startDrag(holder)
                }
                false
            }
            holder.itemView.setOnLongClickListener {
                RenameAppDialog.rename(app, viewModel).show(fragment.childFragmentManager, "SettingsListAdapter")
                renamedOnIndex = position
                true
            }
        } else {
            holder.itemText.text = fragment.getString(R.string.settings_list_item_text)
            holder.itemText.alpha = .3f
            holder.itemButton.visibility = View.VISIBLE
            holder.itemDragger.visibility = View.GONE
            val bundle = Bundle()
            bundle.putInt("index", position)
            holder.itemButton.setOnClickListener(
                    Navigation.createNavigateOnClickListener(R.id.action_openAppsFragment, bundle))
        }
    }

    override fun getItemCount() = if (displayedApps.size != 5) displayedApps.size + 1 else displayedApps.size


    private fun updateApps(newList: List<HomeApp>) {
        val size = displayedApps.size
        displayedApps.clear()
        displayedApps.addAll(newList)
        when {
            size > newList.size -> notifyItemRemoved(deletedFromIndex)
            size < newList.size -> notifyItemRangeChanged(size, displayedApps.size - size)
            renamedOnIndex != -1 -> {
                notifyItemChanged(renamedOnIndex)
                renamedOnIndex = -1
            }
        }
    }

    override fun onViewMoved(oldPosition: Int, newPosition: Int): Boolean {
        if ((oldPosition < displayedApps.size) and (newPosition < displayedApps.size)) {
            val app1 = displayedApps[oldPosition]
            val app2 = displayedApps[newPosition]
            app1.sortingIndex = newPosition
            app2.sortingIndex = oldPosition

            displayedApps[oldPosition] = app2
            displayedApps[newPosition] = app1
            notifyItemMoved(oldPosition, newPosition)
            return true
        }
        return false
    }

    override fun onViewSwiped(position: Int) {
        deletedFromIndex = position
        if (position < displayedApps.size) {
            viewModel.deleteApp(displayedApps[position])
        } else
            notifyDataSetChanged()
    }

    override fun onViewIdle() {
        viewModel.updateApps(displayedApps)
    }

    fun setItemTouchHelper(touchHelper: ItemTouchHelper) {
        this.touchHelper = touchHelper
    }

    inner class AppViewHolder(view: View)// Bind item views here
        : RecyclerView.ViewHolder(view) {
        val itemText: TextView = view.findViewById(R.id.item_text)
        val itemButton: Button = view.findViewById(R.id.item_button)
        val itemDragger: ImageView = view.findViewById(R.id.item_dragger)
    }
}
