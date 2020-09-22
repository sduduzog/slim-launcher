package com.sduduzog.slimlauncher

import android.app.Application
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Provider

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var bindingComponentProvider: Provider<CustomBindingComponentBuilder>

    override fun onCreate() {
        super.onCreate()
        DataBindingUtil.setDefaultComponent(bindingComponentProvider.get().build())
    }
}