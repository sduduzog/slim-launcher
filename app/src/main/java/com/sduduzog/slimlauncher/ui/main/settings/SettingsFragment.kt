package com.sduduzog.slimlauncher.ui.main.settings


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.ui.main.OnItemActionListener
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.settings_fragment.*


class SettingsFragment : Fragment() {

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

    private fun setLightStatusBar(window: Window, view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val flags = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
            window.statusBarColor = Color.WHITE
        }
    }

    private fun clearLightStatusBar(window: Window, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val value = TypedValue()
            context.theme.resolveAttribute(R.attr.colorPrimary, value, true)
            window.statusBarColor = value.data
        }
    }

    override fun onResume() {
        super.onResume()
        val settings = context!!.getSharedPreferences(getString(R.string.prefs_settings), AppCompatActivity.MODE_PRIVATE)
        val active = settings.getInt(getString(R.string.prefs_settings_key_theme), 0)
        if (active == 0) {
            setLightStatusBar(activity!!.window, settings_fragment)
        } else {
            clearLightStatusBar(activity!!.window, context!!)
        }
    }

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
    }
}
