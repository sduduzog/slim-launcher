package com.sduduzog.slimlauncher

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import com.sduduzog.slimlauncher.data.AppDao
import com.sduduzog.slimlauncher.data.DataRoomDatabase

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class DBTest {

    private var mAppDao: AppDao? = null
    private var mDb: DataRoomDatabase? = null

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        mDb = Room.inMemoryDatabaseBuilder(context, DataRoomDatabase::class.java).build()
        mAppDao = mDb?.appDao()
    }

    @After
    fun closeDb() {
        mDb?.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun testInsertLiveDataApps() {
        val app = TestUtil.createApp("TestApp", "com.test.test.app", "TestMainActivity")
        mAppDao?.insert(app)
        val appsInstalled = LiveDataTestUtil.getValue(mAppDao!!.apps)
        assertThat(appsInstalled.size, equalTo(1))
    }
}
