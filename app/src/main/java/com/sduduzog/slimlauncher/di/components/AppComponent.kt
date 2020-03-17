package com.sduduzog.slimlauncher.di.components

import android.app.Application
import com.sduduzog.slimlauncher.App
import com.sduduzog.slimlauncher.di.modules.ActivityModule
import com.sduduzog.slimlauncher.di.modules.DbModule
import com.sduduzog.slimlauncher.di.modules.FragmentModule
import com.sduduzog.slimlauncher.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [
    ActivityModule::class,
    DbModule::class,
    FragmentModule::class,
    ViewModelModule::class,
    AndroidSupportInjectionModule::class
])
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}