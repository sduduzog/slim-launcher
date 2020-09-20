package com.sduduzog.slimlauncher

import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sduduzog.slimlauncher.data.BaseDao
import com.sduduzog.slimlauncher.data.BaseDatabase
import com.sduduzog.slimlauncher.di.AppModule
import com.sduduzog.slimlauncher.di.MainFragmentFactory
import com.sduduzog.slimlauncher.ui.main.HomeFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P], application = HiltTestApplication::class)
@UninstallModules(AppModule::class)
class SomeTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Module
    @InstallIn(ApplicationComponent::class)
    class TestModule {

        @Provides
        @Singleton
        internal fun provideBaseDatabase(application: Application): BaseDatabase {
            return Room.inMemoryDatabaseBuilder(application, BaseDatabase::class.java)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
        }

        @Provides
        @Singleton
        internal fun provideBaseDao(baseDatabase: BaseDatabase): BaseDao {
            return baseDatabase.baseDao()
        }
    }

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun `should display`() {

        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<HomeFragment>(Bundle(), R.style.AppTheme, fragmentFactory) {
            Navigation.setViewNavController(this.view!!, navController)
        }

    }
}