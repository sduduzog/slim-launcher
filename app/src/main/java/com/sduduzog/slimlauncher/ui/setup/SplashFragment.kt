package com.sduduzog.slimlauncher.ui.setup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.sduduzog.slimlauncher.R

class SplashFragment : Fragment(R.layout.splash_fragment) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SplashFragment", "Splash fragment created");
    }
}