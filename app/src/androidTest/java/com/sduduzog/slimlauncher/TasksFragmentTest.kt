package com.sduduzog.slimlauncher


import android.view.KeyEvent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class TasksFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun userCanAddTask() {
        val taskButton = onView(allOf(withText(R.string.home_fragment_tasks), isDisplayed()))
        taskButton.perform(click())
        val inputField = onView(allOf(withHint(R.string.tasks_fragment_enter_a_new_task), isDisplayed()))

        inputField.perform(typeText("Testing"))
        inputField.perform(pressKey(KeyEvent.KEYCODE_ENTER))

        val checkBox = onView(allOf(withText("Testing"), isDisplayed()))

        checkBox.check(matches(isNotChecked()))
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
        appCompatButton2.perform(scrollTo(), click())

        onView(withText(R.string.no_app_selected_toast_msg)).inRoot(
                RootMatchers.withDecorView(Matchers.not(Matchers.`is`(mActivityTestRule.activity.window.decorView))))
                .check(matches(isDisplayed()))
    }

}
