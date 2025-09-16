package com.reza.systemdesign.common

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.reza.systemdesign.ui.common.LoadingItem
import com.reza.systemdesign.ui.util.UiTags
import org.junit.Rule
import org.junit.Test

class CommonTest {

    @get:Rule(order = 0)
    val composeTestRule = createComposeRule()

    @Test
    fun loading_item_is_displayed() {
        composeTestRule.setContent {
            LoadingItem()
        }

        composeTestRule
            .onNodeWithTag(testTag = UiTags.CountriesScreen.PROGRESS_INDICATOR)
            .assertIsDisplayed()
    }
}