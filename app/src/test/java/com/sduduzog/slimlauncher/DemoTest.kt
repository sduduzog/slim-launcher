package com.sduduzog.slimlauncher
import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runner.manipulation.Ordering
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class DemoTest {
    @Test
    fun demoTestFunction() {
        assertThat(true).isTrue()
        assertThat("Cheese").contains("se")
        assertThat(2.0).isGreaterThan(1)
    }

    @Test
    fun initialTest() {
        val context = ApplicationProvider.getApplicationContext<Context>()
    }
}