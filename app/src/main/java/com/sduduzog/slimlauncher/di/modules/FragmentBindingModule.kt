package com.sduduzog.slimlauncher.di.modules

import com.sduduzog.slimlauncher.ui.main.HomeFragment
import com.sduduzog.slimlauncher.ui.options.AddAppFragment
import com.sduduzog.slimlauncher.ui.options.CustomiseAppsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeCustomiseAppsFragment(): CustomiseAppsFragment

    @ContributesAndroidInjector
    abstract fun contributeAddAppFragment(): AddAppFragment
}