package com.sduduzog.slimlauncher

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.sduduzog.slimlauncher.ui.MainActivity2
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class AppTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun shouldStartWithSplashScreenWithNextButton(){
        ActivityScenario.launch(MainActivity2::class.java)


        onView(withText("Slim Launcher")).check(matches(isDisplayed()))
        onView(withText("Next")).check(matches(isDisplayed()))
    }

    @Test
    fun happyPath(){

    }
}