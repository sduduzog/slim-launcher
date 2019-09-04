package com.sduduzog.slimlauncher.utils

import android.os.Bundle
import dagger.android.support.AndroidSupportInjection

abstract class InjectableFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }
}