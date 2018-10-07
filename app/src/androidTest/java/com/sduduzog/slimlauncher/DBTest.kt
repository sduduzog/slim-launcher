package com.sduduzog.slimlauncher

import android.arch.persistence.room.Room
import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import com.sduduzog.slimlauncher.data.App
import com.sduduzog.slimlauncher.data.AppDao
import com.sduduzog.slimlauncher.data.AppRoomDatabase

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat

@RunWith(AndroidJUnit4::class)
class DBTest {

    private var mAppDao: AppDao? = null
    private var mDb: AppRoomDatabase? = null

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        mDb = Room.inMemoryDatabaseBuilder(context, AppRoomDatabase::class.java).build()
        mAppDao = mDb!!.appDao()
    }

    @After
    fun closeDb() {
        mDb!!.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun testInsertLiveDataApps() {
        val app = TestUtil.createApp("TestApp", "com.test.testapp", "TestMainActivity")
        mAppDao!!.insert(app)
        val appsInstalled = LiveDataTestUtil.getValue(mAppDao!!.apps)
        assertThat(appsInstalled.size, equalTo(1))
    }
}
