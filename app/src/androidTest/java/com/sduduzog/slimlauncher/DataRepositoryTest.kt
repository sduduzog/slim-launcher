package com.sduduzog.slimlauncher

import android.app.Application
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.sduduzog.slimlauncher.data.DataRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class DataRepositoryTest {

    private var repository: DataRepository? = null

    @Before
    fun createRepository(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        repository = DataRepository.getInstance(context as Application)
    }

    @After
    fun closeRepository(){
        repository = null
    }

    @Test
    fun assertList(){
        val list = listOf(1, 2, 3, 4, 5)
        val newList = list.distinctBy {
            it == 2
        }
    }
}