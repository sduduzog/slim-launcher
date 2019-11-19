package com.sduduzog.slimlauncher.di.modules

import com.sduduzog.slimlauncher.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [
    FragmentBindingModule::class
])
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}