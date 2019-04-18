package com.sduduzog.slimlauncher.ui.options

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.adapters.CustomAppsAdapter
import com.sduduzog.slimlauncher.data.HomeApp
import com.sduduzog.slimlauncher.data.MainViewModel
import com.sduduzog.slimlauncher.dialogs.RenameAppDialog
import com.sduduzog.slimlauncher.ui.BaseFragment
import com.sduduzog.slimlauncher.utils.OnItemActionListener
import com.sduduzog.slimlauncher.utils.OnShitDoneToAppsListener
import kotlinx.android.synthetic.main.customise_apps_fragment.*


class CustomiseAppsFragment : BaseFragment(), OnShitDoneToAppsListener {

    override fun getFragmentView(): View = customise_apps_fragment as View

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.customise_apps_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = CustomAppsAdapter(this)
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        } ?: throw Error("Activity null, something here is fucked up")

        viewModel.apps.observe(this, Observer {
            it?.let { apps ->
                adapter.setItems(apps)
                when (apps.size) {
                    in 0..6 -> {
                        customise_apps_fragment_add.visibility = View.VISIBLE
                    }
                    else -> {
                        customise_apps_fragment_add.visibility = View.GONE
                    }
                }
                val count = 7 - apps.size
                customise_apps_fragment_counter.text = resources.getQuantityString(R.plurals.slots_plurals, count, count)
            } ?: adapter.setItems(listOf())
        })


        customise_apps_fragment_list.adapter = adapter
        val listener: OnItemActionListener = adapter
        val simpleItemTouchCallback = object : ItemTouchHelper.Callback() {

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView,
                                     viewHolder: RecyclerView.ViewHolder, dX: Float,
                                     dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                if (isCurrentlyActive) {
                    viewHolder.itemView.alpha = 0.5f
                } else {
                    viewHolder.itemView.alpha = 1f
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                listener.onViewIdle()
            }

            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = 0
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return listener.onViewMoved(viewHolder.adapterPosition, target.adapterPosition)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listener.onViewSwiped(viewHolder.adapterPosition)
            }

            override fun isLongPressDragEnabled() = false
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)

        itemTouchHelper.attachToRecyclerView(customise_apps_fragment_list)

        adapter.setItemTouchHelper(itemTouchHelper)

        customise_apps_fragment_add.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_customiseAppsFragment_to_addAppFragment))
    }

    private fun showPopupMenu(view: View): PopupMenu {
        val popup = PopupMenu(context!!, view)
        popup.menuInflater.inflate(R.menu.customise_apps_popup_menu, popup.menu)
        popup.show()
        return popup
    }

    override fun onAppsUpdated(list: List<HomeApp>) {
        viewModel.update(*list.toTypedArray())
    }

    override fun onAppMenuClicked(view: View, app: HomeApp) {
        showPopupMenu(view).setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.ca_menu_rename -> {
                    RenameAppDialog.rename(app, viewModel).show(childFragmentManager, "SettingsListAdapter")
                }
                R.id.ca_menu_remove -> {
                    viewModel.remove(app)
                }
            }
            true
        }
    }
}