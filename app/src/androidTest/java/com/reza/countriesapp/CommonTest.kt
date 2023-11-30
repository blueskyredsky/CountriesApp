package com.reza.countriesapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.reza.countriesapp.presentation.common.LoadingItem
import com.reza.countriesapp.util.Constants

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CommonTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @Test
    fun loading_item_is_displayed() {
        composeTestRule.setContent {
            LoadingItem()
        }

        composeTestRule
            .onNodeWithTag(testTag = Constants.UiTags.ProgressIndicator.customName)
            .assertIsDisplayed()
    }

    /*@Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.reza.countriesapp", appContext.packageName)
    }*/
}