package com.sduduzog.slimlauncher

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.sduduzog.slimlauncher.data.AppDao
import com.sduduzog.slimlauncher.data.DataRoomDatabase
import com.sduduzog.slimlauncher.data.model.HomeApp
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4ClassRunner::class)
class DBTest {

    private var mAppDao: AppDao? = null
    private var mDb: DataRoomDatabase? = null

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        mDb = Room.inMemoryDatabaseBuilder(context, DataRoomDatabase::class.java).build()
        mAppDao = mDb!!.appDao()
    }

    @After
    fun closeDb() {
        mDb?.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun testInsertLiveDataApps() {
        val app = HomeApp("TestApp", "com.test.test.app", "TestMainActivity", 0)
        mAppDao!!.add(app)

        var appsInstalled: List<HomeApp> = listOf()

        val latch = CountDownLatch(1)

        val appsLiveData = mAppDao!!.apps

        val observer = Observer<List<HomeApp>> {
            appsInstalled = it
            latch.countDown()
        }

        appsLiveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        appsLiveData.removeObserver(observer)
        assertThat(appsInstalled.size, equalTo(1))
    }
}
