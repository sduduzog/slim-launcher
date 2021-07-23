package com.sduduzog.slimlauncher
import com.google.common.truth.Truth.assertThat
import org.junit.Test


class DemoTest {
    @Test
    fun demoTestFunction() {
        assertThat(true).isTrue()
        assertThat("Cheese").contains("se")
        assertThat(2.0).isGreaterThan(1)
    }
}