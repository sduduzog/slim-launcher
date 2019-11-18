package com.sduduzog.slimlauncher.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sduduzog.slimlauncher.models.AddAppViewModel
import com.sduduzog.slimlauncher.models.CustomiseAppsViewModel
import com.sduduzog.slimlauncher.models.MainViewModel
import com.sduduzog.slimlauncher.utils.ViewModelFactory
import com.sduduzog.slimlauncher.utils.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

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
    @ViewModelKey(CustomiseAppsViewModel::class)
    protected abstract fun customiseAppsViewModel(customiseAppsViewModel: CustomiseAppsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddAppViewModel::class)
    protected abstract fun addAppViewModel(addAppViewModel: AddAppViewModel): ViewModel
}