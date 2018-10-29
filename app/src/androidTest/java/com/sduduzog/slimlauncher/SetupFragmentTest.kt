package com.sduduzog.slimlauncher


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SetupFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun isAlertDialogShown() {
        val appCompatButton = onView(
                allOf(withId(R.id.setupButton),
                        withText("start"), isDisplayed()))
        appCompatButton.perform(click())

        val frameLayout = onView(
                allOf(withId(android.R.id.content), isDisplayed()))
        frameLayout.check(matches(isDisplayed()))

        val alertDialogTitle = onView(
                allOf(withText(R.string.choose_apps_title)))
        alertDialogTitle.check(matches(isDisplayed()))
    }

    @Test
    fun noAppsSelected() {
        val appCompatButton = onView(
                allOf(withId(R.id.setupButton), withText("start"), isDisplayed()))
        appCompatButton.perform(click())

        val appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("Done")))
        appCompatButton2.perform(ViewActions.scrollTo(), click())

        val viewGroup = onView(
                allOf(withId(R.id.setupContainer), isDisplayed()))
        viewGroup.check(matches(isDisplayed()))

        onView(withText(R.string.no_app_selected_toast_msg)).inRoot(
                RootMatchers.withDecorView(Matchers.not(Matchers.`is`(mActivityTestRule.activity.window.decorView))))
                .check(matches(isDisplayed()))
    }

}
