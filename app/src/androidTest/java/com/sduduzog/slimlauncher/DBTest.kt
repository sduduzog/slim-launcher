package com.sduduzog.slimlauncher

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import com.sduduzog.slimlauncher.ui.main.model.AppDao
import com.sduduzog.slimlauncher.ui.main.model.AppRoomDatabase

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
        val app = TestUtil.createApp("TestApp", "com.test.test.app", "TestMainActivity")
        mAppDao!!.insert(app)
        val appsInstalled = LiveDataTestUtil.getValue(mAppDao!!.apps)
        assertThat(appsInstalled.size, equalTo(1))
    }
}
