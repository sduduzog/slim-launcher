package com.sduduzog.slimlauncher


import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
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
//        val taskButton = onView(allOf(withText(R.string.home_fragment_tasks), isDisplayed()))
//        taskButton.perform(click())
//        val inputField = onView(allOf(withHint(R.string.tasks_fragment_enter_a_new_task), isDisplayed()))
//
//        inputField.perform(typeText("Testing"))
//        inputField.perform(pressKey(KeyEvent.KEYCODE_ENTER))
//
//        val checkBox = onView(allOf(withText("Testing"), isDisplayed()))
//
//        checkBox.check(matches(isNotChecked()))
    }
}
