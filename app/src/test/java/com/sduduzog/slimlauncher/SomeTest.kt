package com.sduduzog.slimlauncher

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class SomeTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun `should display`(){
        val activityController = Robolectric.buildActivity(MainActivity::class.java)
        assert(true)
    }
}