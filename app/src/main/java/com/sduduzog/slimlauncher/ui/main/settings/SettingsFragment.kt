package com.sduduzog.slimlauncher.ui.main.settings


import android.content.Context.MODE_PRIVATE
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.ui.main.OnItemActionListener
import com.sduduzog.slimlauncher.ui.main.StatusBarThemeFragment
import kotlinx.android.synthetic.main.settings_fragment.*


class SettingsFragment : StatusBarThemeFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = SettingsListAdapter(this)
        settingsAppList.adapter = adapter
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
                val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return listener.onViewMoved(viewHolder.adapterPosition, target.adapterPosition)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //adapter.deleteAppFromList(viewHolder.adapterPosition)
                listener.onViewSwiped(viewHolder.adapterPosition)
            }

            override fun isLongPressDragEnabled() = false
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)

        itemTouchHelper.attachToRecyclerView(settingsAppList)

        adapter.setItemTouchHelper(itemTouchHelper)

        buttonChangeTheme.setOnClickListener {
            val themeChooserDialog = ThemeChooserDialog.getThemeChooser()
            themeChooserDialog.showNow(fragmentManager, "THEME_CHOOSER")
        }
        initComponents()
    }

    override fun getFragmentView(): View = settings_fragment

    private fun initComponents() {
        val settings = context!!.getSharedPreferences(getString(R.string.prefs_settings), MODE_PRIVATE)
        clockSwitch.isChecked = settings.getBoolean(getString(R.string.prefs_settings_key_clock_type), false)
        clockSwitch.setOnCheckedChangeListener { _, b ->
            settings.edit {
                putBoolean(getString(R.string.prefs_settings_key_clock_type), b)
            }
        }

        dialerSwitch.isChecked = settings.getBoolean(getString(R.string.prefs_settings_key_app_dialer), false)
        dialerSwitch.setOnCheckedChangeListener { _, b ->
            settings.edit {
                putBoolean(getString(R.string.prefs_settings_key_app_dialer), b)
            }
        }

        statusBarSwitch.isChecked = settings.getBoolean(getString(R.string.prefs_settings_key_hide_status_bar), false)
        statusBarSwitch.setOnCheckedChangeListener { _, b ->
            settings.edit {
                putBoolean(getString(R.string.prefs_settings_key_hide_status_bar), b)
            }
        }
    }
}
