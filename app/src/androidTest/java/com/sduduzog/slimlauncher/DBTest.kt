package com.sduduzog.slimlauncher

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.sduduzog.slimlauncher.data.BaseDao
import com.sduduzog.slimlauncher.data.BaseDatabase
import com.sduduzog.slimlauncher.data.model.HomeApp
import com.sduduzog.slimlauncher.data.model.Note
import com.sduduzog.slimlauncher.data.model.Task
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4ClassRunner::class)
class DBTest {

    private var baseDao: BaseDao? = null
    private var mDb: BaseDatabase? = null

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        mDb = Room.inMemoryDatabaseBuilder(context, BaseDatabase::class.java).build()
        baseDao = mDb!!.baseDao()
    }

    @After
    fun closeDb() {
        mDb?.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun testInsertLiveDataApps() {
        val app = HomeApp("TestApp", "com.test.test.app", "TestMainActivity", 0)
        baseDao!!.add(app)

        var appsInstalled: List<HomeApp> = listOf()

        val latch = CountDownLatch(1)

        val appsLiveData = baseDao!!.apps

        val observer = Observer<List<HomeApp>> {
            appsInstalled = it
            latch.countDown()
        }

        appsLiveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        appsLiveData.removeObserver(observer)
        assertThat(appsInstalled.size, equalTo(1))
    }

    @Test
    @Throws(InterruptedException::class)
    fun testInsertLiveDataNotes() {
        val note = Note("TestNote", Date().time)
        baseDao!!.add(note)

        var notes: List<Note> = listOf()

        val latch = CountDownLatch(1)

        val notesLiveData = baseDao!!.notes

        val observer = Observer<List<Note>> {
            notes = it
            latch.countDown()
        }

        notesLiveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        notesLiveData.removeObserver(observer)
        assertThat(notes.size, equalTo(1))
    }

    @Test
    @Throws(InterruptedException::class)
    fun testInsertLiveDataTasks() {
        val task = Task("TestTask", false, 0)
        baseDao!!.add(task)

        var tasks: List<Task> = listOf()

        val latch = CountDownLatch(1)

        val tasksLiveData = baseDao!!.tasks

        val observer = Observer<List<Task>> {
            tasks = it
            latch.countDown()
        }

        tasksLiveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        tasksLiveData.removeObserver(observer)
        assertThat(tasks.size, equalTo(1))
    }
}
