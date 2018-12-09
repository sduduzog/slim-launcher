package com.sduduzog.slimlauncher.ui.main.setup


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sduduzog.slimlauncher.R
import kotlinx.android.synthetic.main.setup_fragment.*


class SetupFragment : Fragment() {

    private lateinit var onPagerListener: OnPagerListener
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.setup_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setup_view_pager.adapter = SectionsPagerAdapter(childFragmentManager)

        setup_view_pager.setOnTouchListener { _, _ -> true }
    }

    override fun onStart() {
        super.onStart()
        onPagerListener = object : OnPagerListener {
            override fun onPage(position: Int) {
                setup_view_pager.currentItem = position
            }
        }
    }

//    private fun checkFreshInstall() {
//        val settings = activity!!.getSharedPreferences(getString(R.string.prefs_settings), MODE_PRIVATE)
//        if (settings.getBoolean(getString(R.string.prefs_settings_key_fresh_install_setup), true)) {
//            val pm = activity!!.packageManager
//            val main = Intent(Intent.ACTION_MAIN, null)
//
//            main.addCategory(Intent.CATEGORY_LAUNCHER)
//
//            val launchables = pm.queryIntentActivities(main, 0)
//            Collections.sort(launchables,
//                    ResolveInfo.DisplayNameComparator(pm))
//            for (i in launchables.indices) {
//                val item = launchables[i]
//                val activity = item.activityInfo
//                val app = App(launchables[i].loadLabel(pm).toString(), activity.applicationInfo.packageName, activity.name)
//                apps.add(app)
//            }
//            revealUI(state)
//        } else {
//            finishSetup()
//        }
//    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                1 -> HomeSetupFragment.newInstance().apply {
                    this.listener = onPagerListener
                }
                2 -> ClockSetupFragment.newInstance().apply {
                    this.listener = onPagerListener
                }
                3 -> DialerSetupFragment.newInstance().apply {
                    this.listener = onPagerListener
                }
                4 -> ThemeSetupFragment.newInstance().apply {
                    this.listener = onPagerListener
                }
                else -> SplashFragment.newInstance().apply {
                    this.listener = onPagerListener
                }
            }
        }

        override fun getCount(): Int {
            return 5
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "Splash screen"
                1 -> return "Home setup"
                2 -> return "Clock setup"
                3 -> return "Theme setup"
                4 -> return "Dialer setup"
            }
            return null
        }
    }
}
