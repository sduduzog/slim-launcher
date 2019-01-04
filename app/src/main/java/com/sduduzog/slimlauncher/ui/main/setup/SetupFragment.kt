package com.sduduzog.slimlauncher.ui.main.setup


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.ui.main.StatusBarThemeFragment
import kotlinx.android.synthetic.main.setup_fragment.*


class SetupFragment : StatusBarThemeFragment() {

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

    override fun getFragmentView(): View = setup_view_pager

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
