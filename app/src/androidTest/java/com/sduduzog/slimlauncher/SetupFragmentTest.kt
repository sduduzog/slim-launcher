package com.sduduzog.slimlauncher


import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class SetupFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var sharedPreferences: SharedPreferences

    @Before
    fun clearPreferences() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.prefs_settings), MODE_PRIVATE)
        sharedPreferences.edit().clear().commit()
    }


    @Test
    fun isAlertDialogShown() {
        val appCompatButton = onView(
                allOf(withText(R.string.setup_button_start), isDisplayed()))
        appCompatButton.perform(click())


        val frameLayout = onView(
                allOf(withId(android.R.id.content), isDisplayed()))
        frameLayout.check(matches(isDisplayed()))

        val alertDialogTitle = onView(
                allOf(withText(R.string.choose_apps_title)))
        alertDialogTitle.check(matches(isDisplayed()))
    }

    @Test
    fun isStartButtonShown() {
        val button = onView(allOf(withText(R.string.setup_button_start)))
        button.check(matches(isDisplayed()))
    }

    @Test
    fun noAppsSelected() {
        val startButton = onView(allOf(withText(R.string.setup_button_start)))
        startButton.perform(click())

        val appCompatButton2 = onView(
                allOf(withText("DONE")))
        appCompatButton2.perform(ViewActions.scrollTo(), click())

        onView(withText(R.string.no_app_selected_toast_msg)).inRoot(
                RootMatchers.withDecorView(Matchers.not(Matchers.`is`(mActivityTestRule.activity.window.decorView))))
                .check(matches(isDisplayed()))
    }

}
