package com.sduduzog.slimlauncher.setup

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.sduduzog.slimlauncher.R
import com.sduduzog.slimlauncher.launchFragmentInHiltContainer
import com.sduduzog.slimlauncher.ui.setup.SplashFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

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

        onView(withText("Slim Launcher")).check(matches(isDisplayed()))
        onView(withText("Next")).check(matches(isDisplayed()))
        onView(withText("v3.0 (Beta)")).check(matches(isDisplayed()))
    }
}