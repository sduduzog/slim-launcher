package com.sduduzog.slimlauncher.ui.options

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.adapters.CustomAppsAdapter
import com.sduduzog.slimlauncher.databinding.CustomiseAppsFragmentBinding
import com.sduduzog.slimlauncher.models.CustomiseAppsViewModel
import com.sduduzog.slimlauncher.models.HomeApp
import com.sduduzog.slimlauncher.ui.dialogs.RemoveAllAppsDialog
import com.sduduzog.slimlauncher.ui.dialogs.RenameAppDialog
import com.sduduzog.slimlauncher.utils.BaseFragment
import com.sduduzog.slimlauncher.utils.OnItemActionListener
import com.sduduzog.slimlauncher.utils.OnShitDoneToAppsListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CustomiseAppsFragment : BaseFragment(), OnShitDoneToAppsListener {

    private var _binding: CustomiseAppsFragmentBinding? = null
    private val binding get() = _binding
    override fun getFragmentView(): ViewGroup = binding!!.root

    private val viewModel: CustomiseAppsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomiseAppsFragmentBinding.inflate(inflater, container, false)

        val adapter = CustomAppsAdapter(this)

        val settings = activity!!.applicationContext.getSharedPreferences(getString(R.string.prefs_settings), AppCompatActivity.MODE_PRIVATE)
        val limit = settings.getInt(getString(R.string.prefs_settings_key_apps_limit), 7)

        viewModel.apps.observe(viewLifecycleOwner) {
            it?.let { apps ->
                adapter.setItems(apps)
                binding!!.customiseAppsFragmentAdd.visibility =
                    if (apps.size < limit) View.VISIBLE else View.INVISIBLE
            } ?: adapter.setItems(listOf())
        }
        binding!!.customiseAppsFragmentRemoveAll.setOnClickListener {
            RemoveAllAppsDialog.getInstance(viewModel.apps.value!!, viewModel)
                .show(childFragmentManager, "REMOVE_APPS")
        }

        binding!!.customiseAppsFragmentList.adapter = adapter
        val listener: OnItemActionListener = adapter
        val simpleItemTouchCallback = object : ItemTouchHelper.Callback() {

            override fun onChildDraw(
                c: Canvas, recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder, dX: Float,
                dY: Float, actionState: Int, isCurrentlyActive: Boolean
            ) {
                if (isCurrentlyActive) {
                    viewHolder.itemView.alpha = 0.5f
                } else {
                    viewHolder.itemView.alpha = 1f
                }
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                listener.onViewIdle()
            }

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags = 0
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return listener.onViewMoved(viewHolder.adapterPosition, target.adapterPosition)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listener.onViewSwiped(viewHolder.adapterPosition)
            }

            override fun isLongPressDragEnabled() = false
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)

        itemTouchHelper.attachToRecyclerView(binding!!.customiseAppsFragmentList)

        adapter.setItemTouchHelper(itemTouchHelper)

        binding!!.customiseAppsFragmentAdd.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_customiseAppsFragment_to_addAppFragment
            )
        )
        return binding?.root
    }

    private fun showPopupMenu(view: View): PopupMenu {
        val popup = PopupMenu(requireContext(), view)
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
                    RenameAppDialog.getInstance(app, viewModel)
                        .show(childFragmentManager, "SettingsListAdapter")
                }
                R.id.ca_menu_remove -> {
                    viewModel.remove(app)
                }
                R.id.ca_menu_reset -> {
                    viewModel.reset(app)
                }
            }
            true
        }
    }
}