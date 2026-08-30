package com.sduduzog.slimlauncher.di

import android.app.Application
import androidx.room.Room
import com.sduduzog.slimlauncher.data.BaseDao
import com.sduduzog.slimlauncher.data.BaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    internal fun provideBaseDatabase(application: Application): BaseDatabase {
        return Room.databaseBuilder(application,
                BaseDatabase::class.java, "app_database")
                .addMigrations(*BaseDatabase.ALL_MIGRATIONS)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideBaseDao(baseDatabase: BaseDatabase): BaseDao {
        return baseDatabase.baseDao()
    }
}