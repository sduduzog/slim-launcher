package com.sduduzog.slimlauncher.di.module

import android.app.Application
import androidx.room.Room
import com.sduduzog.slimlauncher.data.BaseDatabase
import com.sduduzog.slimlauncher.data.ConfigDao
import com.sduduzog.slimlauncher.data.dao.AppDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): BaseDatabase {
        return Room.databaseBuilder(application, BaseDatabase::class.java, "app_database")
                .addMigrations(*BaseDatabase.getMigrations())
                .build()
    }

    @Provides
    @Singleton
    internal fun provideConfigDao(baseDatabase: BaseDatabase): ConfigDao = baseDatabase.configDao()

    @Provides
    @Singleton
    internal fun provideAppDao(baseDatabase: BaseDatabase): AppDao = baseDatabase.appDao()
}