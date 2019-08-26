package com.sduduzog.slimlauncher.di.component

import android.app.Application
import com.sduduzog.slimlauncher.AppController
import com.sduduzog.slimlauncher.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [
    BaseModule::class,
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