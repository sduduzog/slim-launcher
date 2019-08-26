package com.sduduzog.slimlauncher.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sduduzog.slimlauncher.factory.ViewModelFactory
import com.sduduzog.slimlauncher.ui.MainViewModel
import com.sduduzog.slimlauncher.ui.home.HomeViewModel
import com.sduduzog.slimlauncher.ui.options.AddAppViewModel
import com.sduduzog.slimlauncher.ui.options.CustomiseAppsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    protected abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddAppViewModel::class)
    protected abstract fun addAppViewModel(addAppViewModel: AddAppViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    protected abstract fun addHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CustomiseAppsViewModel::class)
    protected abstract fun addCustomiseAppsViewModel(customiseAppsViewModel: CustomiseAppsViewModel): ViewModel
}