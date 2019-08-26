package com.sduduzog.slimlauncher.di.module

import com.sduduzog.slimlauncher.data.ConfigDao
import com.sduduzog.slimlauncher.data.dao.AppDao
import com.sduduzog.slimlauncher.data.repository.AppRepository
import com.sduduzog.slimlauncher.data.repository.ConfigRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BaseModule {
    @Provides
    @Singleton
    internal fun provideConfigRepository(configDao: ConfigDao):
            ConfigRepository = ConfigRepository(configDao)

    @Provides
    @Singleton
    internal fun provideAppRepository(appDao: AppDao):
            AppRepository = AppRepository((appDao))
}