package com.sduduzog.slimlauncher.di

import com.sduduzog.slimlauncher.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [
    FragmentBindingModule::class
])
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}