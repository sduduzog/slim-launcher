package com.sduduzog.slimlauncher.di.component

import android.app.Application
import com.sduduzog.slimlauncher.AppController
import com.sduduzog.slimlauncher.di.module.ActivityModule
import com.sduduzog.slimlauncher.di.module.DBModule
import com.sduduzog.slimlauncher.di.module.FragmentModule
import com.sduduzog.slimlauncher.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [
    DBModule::class,
    ActivityModule::class,
    FragmentModule::class,
    ViewModelModule::class,
    AndroidSupportInjectionModule::class]
)
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(appController: AppController)
}