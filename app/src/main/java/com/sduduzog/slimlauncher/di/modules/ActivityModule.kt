package com.sduduzog.slimlauncher.di.modules

import com.sduduzog.slimlauncher.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity
}