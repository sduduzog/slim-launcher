package com.sduduzog.slimlauncher

import com.sduduzog.slimlauncher.ui.main.HomeFragment
import com.sduduzog.slimlauncher.ui.options.AddAppFragment
import com.sduduzog.slimlauncher.ui.options.CustomiseAppsFragment
import com.sduduzog.slimlauncher.ui.options.OptionsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributesHomeFragment() : HomeFragment
    @ContributesAndroidInjector

    abstract fun contributesOptionsFragment() : OptionsFragment
    @ContributesAndroidInjector

    abstract fun contributesCustomiseAppsFragment() : CustomiseAppsFragment
    @ContributesAndroidInjector

    abstract fun contributesAddAppFragment() : AddAppFragment
}