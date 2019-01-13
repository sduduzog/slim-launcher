package com.sduduzog.slimlauncher.ui.main.setup


import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.App
import com.sduduzog.slimlauncher.ui.main.MainViewModel
import kotlinx.android.synthetic.main.splash_fragment.*

class SplashFragment : PagerHelperFragment(), ChooseAppsDialog.Companion.OnChooseAppsListener {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setup_splash_button_start.setOnClickListener {
                ChooseAppsDialog.getInstance(this).show(childFragmentManager, "HomeSetupFragment")
        }
        val settings = activity!!.getSharedPreferences(getString(R.string.prefs_settings), MODE_PRIVATE)
        if (!settings.getBoolean(getString(R.string.prefs_settings_key_fresh_install_setup), true)) {
            Navigation.findNavController(splash_fragment).navigate(R.id.action_setupFragment_to_mainFragment2)
        }
    }

        override fun onChooseApps(apps: List<App>) {
            viewModel.clearHomeApps()
            viewModel.addToHomeScreen(apps)
            listener?.onPage(1) // Move to next section
        }


    companion object {
        @JvmStatic
        fun newInstance() = SplashFragment()
    }
}
