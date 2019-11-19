package com.sduduzog.slimlauncher.di.components

import android.app.Application
import com.sduduzog.slimlauncher.AppController
import com.sduduzog.slimlauncher.di.modules.ActivityBindingModule
import com.sduduzog.slimlauncher.di.modules.DatabaseModule
import com.sduduzog.slimlauncher.di.modules.ViewModelModule
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
interface AppComponent : AndroidInjector<AppController> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(appController: AppController)
}