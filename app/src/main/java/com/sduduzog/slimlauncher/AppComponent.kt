package com.sduduzog.slimlauncher

import android.app.Application
import com.sduduzog.slimlauncher.di.ActivityBindingModule
import com.sduduzog.slimlauncher.di.DatabaseModule
import com.sduduzog.slimlauncher.di.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [
    AndroidSupportInjectionModule::class,
    DatabaseModule::class,
    ViewModelModule::class,
    ActivityBindingModule::class
])
@Singleton
interface AppComponent : AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(mainApplication: MainApplication)
}