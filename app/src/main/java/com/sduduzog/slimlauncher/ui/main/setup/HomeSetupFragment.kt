package com.sduduzog.slimlauncher.ui.main.setup


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.data.App
import com.sduduzog.slimlauncher.ui.main.MainViewModel
import kotlinx.android.synthetic.main.home_setup_fragment.*


class HomeSetupFragment : PagerHelperFragment(), ChooseAppsDialog.Companion.OnChooseAppsListener {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_setup_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setup_choose_button.setOnClickListener {
            ChooseAppsDialog.getInstance(this).show(childFragmentManager, "HomeSetupFragment")
        }
    }

    override fun onChooseApps(apps: List<App>) {
        viewModel.clearHomeApps()
        viewModel.addToHomeScreen(apps)
        listener?.onPage(2) // Move to next section
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeSetupFragment()
    }
}
