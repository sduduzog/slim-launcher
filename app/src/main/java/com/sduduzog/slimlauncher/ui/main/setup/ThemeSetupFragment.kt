package com.sduduzog.slimlauncher.ui.main.setup


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import kotlinx.android.synthetic.main.theme_setup_fragment.*


class ThemeSetupFragment : PagerHelperFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.theme_setup_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setup_fab_candy.setOnClickListener(this)
        setup_fab_dark.setOnClickListener(this)
        setup_fab_default.setOnClickListener(this)
        setup_fab_jupiter.setOnClickListener(this)
        setup_fab_pastel.setOnClickListener(this)
        setup_fab_teal.setOnClickListener(this)

        theme_setup_button.setOnClickListener {
            val settings = activity!!.getSharedPreferences(getString(R.string.prefs_settings), MODE_PRIVATE)
            settings.edit {
                putBoolean(getString(R.string.prefs_settings_key_fresh_install_setup), false)
            }
            Navigation.findNavController(theme_setup_fragment).navigate(R.id.action_setupFragment_to_mainFragment2)
        }
    }

    override fun onClick(view: View) {
        val index = when (view.id) {
            R.id.setup_fab_dark -> 1
            R.id.setup_fab_jupiter -> 2
            R.id.setup_fab_teal -> 3
            R.id.setup_fab_candy -> 4
            R.id.setup_fab_pastel -> 5
            else -> 0
        }
        val settings = activity!!.getSharedPreferences(getString(R.string.prefs_settings), Context.MODE_PRIVATE)
        settings.edit {
            putInt(getString(R.string.prefs_settings_key_theme), index)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThemeSetupFragment()
    }
}
