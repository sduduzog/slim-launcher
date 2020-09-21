package com.sduduzog.slimlauncher.ui.setup

import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.SplashGraphDirections

class SplashFragment : Fragment(R.layout.splash_fragment) {


    private val handler = Handler();

    private val finishSplash: Runnable = Runnable {
        Navigation.findNavController(requireView()).navigate(SplashGraphDirections.splashToSetup())
    }

    override fun onStart() {
        super.onStart()
        handler.postDelayed(finishSplash, 1000L)
    }

    override fun onStop() {
        handler.removeCallbacks(finishSplash)
        super.onStop()
    }
}