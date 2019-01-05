package com.sduduzog.slimlauncher.ui.main.setup


import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import kotlinx.android.synthetic.main.splash_fragment.*

class SplashFragment : PagerHelperFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setup_splash_button_start.setOnClickListener {
            listener?.onPage(1) // Move to next item in pager
        }
        val settings = activity!!.getSharedPreferences(getString(R.string.prefs_settings), MODE_PRIVATE)
        if (!settings.getBoolean(getString(R.string.prefs_settings_key_fresh_install_setup), true)) {
            Navigation.findNavController(splash_fragment).navigate(R.id.action_setupFragment_to_mainFragment2)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SplashFragment()
    }
}
