package com.sduduzog.slimlauncher.di.module

import android.app.Application
import androidx.room.Room
import com.sduduzog.slimlauncher.data.AppDatabase
import com.sduduzog.slimlauncher.data.dao.AppDao
import com.sduduzog.slimlauncher.data.dao.ConfigDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
                .addMigrations(*AppDatabase.getMigrations())
                .build()
    }

    @Provides
    @Singleton
    internal fun provideConfigDao(appDatabase: AppDatabase): ConfigDao = appDatabase.configDao()

    @Provides
    @Singleton
    internal fun provideAppDao(appDatabase: AppDatabase): AppDao = appDatabase.appDao()
}