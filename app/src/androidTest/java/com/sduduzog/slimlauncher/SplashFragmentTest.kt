package com.sduduzog.slimlauncher

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.sduduzog.slimlauncher.ui.setup.SplashFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class SplashFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun shouldBeFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<SplashFragment>(Bundle(), R.style.AppTheme2) {
            Navigation.setViewNavController(this.view!!, navController)
        }

        Espresso.onView(ViewMatchers.withText("Slim Launcher")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Next")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}