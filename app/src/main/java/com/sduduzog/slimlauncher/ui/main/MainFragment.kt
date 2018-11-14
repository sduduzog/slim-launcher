package com.sduduzog.slimlauncher.ui.main

import android.animation.ObjectAnimator
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.Navigation
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HALF_EXPANDED
import com.sduduzog.slimlauncher.MainActivity
import com.sduduzog.slimlauncher.R
import kotlinx.android.synthetic.main.main_bottom_sheet.*
import kotlinx.android.synthetic.main.main_content.*


class MainFragment : Fragment() {

    private lateinit var sheetBehavior: BottomSheetBehavior<FrameLayout>
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    @Suppress("PropertyName")
    val TAG: String = "MainFragment"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated")
        mSectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager)
        container.adapter = mSectionsPagerAdapter
        sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        optionsView.alpha = 0.0f
        setEventListeners()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
        doBounceAnimation(ivExpand)
        sheetBehavior.state = STATE_COLLAPSED
        mSectionsPagerAdapter?.notifyDataSetChanged()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        with(context as MainActivity) {
            this.onBackPressedListener = object : MainActivity.OnBackPressedListener {
                override fun onBackPressed() {
                    sheetBehavior.state = STATE_COLLAPSED
                }
            }
        }
    }

    private fun doBounceAnimation(targetView: View) {
        val animator = ObjectAnimator.ofFloat(targetView, "translationY", 0f, -20f, 0f)
        animator.interpolator = EasingInterpolator(Ease.QUINT_OUT)
        animator.startDelay = 1000
        animator.duration = 1000
        animator.repeatCount = 1
        animator.start()
    }

    private fun rateApp() {
        val uri = Uri.parse("market://details?id=" + context?.packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
        }
        try {
            startActivity(goToMarket)
            Log.d(TAG, goToMarket.data?.query)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context?.packageName)))
        }
    }

    private fun openSettings() {
        startActivity(Intent(android.provider.Settings.ACTION_SETTINGS))
    }

    private fun setEventListeners() {
        bottomSheet.setOnClickListener {

        }
        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
                val multi = 3 * p1
                optionsView.alpha = multi
                optionsView.cardElevation = p1 * 8
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    optionsView.elevation = p1 * 8
                }
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                iconTray.visibility = View.GONE
                if (newState == STATE_COLLAPSED) {
                    iconTray.visibility = View.VISIBLE
                }
            }
        })
        settingsText.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_openSettingsFragment))
        deviceSettingsText.setOnClickListener { openSettings() }
        rateAppText.setOnClickListener { rateApp() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            changeLauncherText.setOnClickListener {
                startActivity(Intent(Settings.ACTION_HOME_SETTINGS))
            }
        } else changeLauncherText.visibility = View.GONE
        aboutText.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_openAboutFragment))
        ivCall.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_DIAL)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e(TAG, e.message)
            }
        }
        ivCamera.setOnClickListener {
            try {
                val intent = Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e(TAG, e.message)
            }
        }

        ivExpand.setOnClickListener {
            if (sheetBehavior.state == STATE_COLLAPSED) sheetBehavior.state = STATE_HALF_EXPANDED
        }
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return HomeFragment.newInstance()
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 1
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "Home"
            }
            return null
        }
    }
}
