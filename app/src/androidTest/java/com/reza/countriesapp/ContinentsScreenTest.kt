package com.reza.countriesapp

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.reza.countriesapp.presentation.MainActivity
import com.reza.systemdesign.ui.util.UiTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ContinentsScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule.onNodeWithTag(UiTags.HomeScreen.ROOT).assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun verify_shimmerAndThenItemsAreDisplayed() {
        // the waitUntil APIs
        composeTestRule.waitUntilDoesNotExist(hasTestTag(UiTags.HomeScreen.SHIMMER_LAZY_COLUMN))

        composeTestRule.onAllNodes(
            hasTestTag(UiTags.HomeScreen.CONTINENT_ITEM)
        ).onFirst().assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun appNavHost_verifyNavigateToDetailsDestination() {
        // the waitUntil APIs
        composeTestRule.waitUntilDoesNotExist(hasTestTag(UiTags.HomeScreen.SHIMMER_LAZY_COLUMN))

        composeTestRule.onAllNodes(
            hasTestTag(UiTags.HomeScreen.CONTINENT_ITEM)
        ).onFirst().performClick()

        composeTestRule.onNodeWithTag(UiTags.DetailsScreen.ROOT).assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun verify_itemsOnDetailsScreenAreDisplayed() {
        // the waitUntil APIs
        composeTestRule.waitUntilDoesNotExist(hasTestTag(UiTags.HomeScreen.SHIMMER_LAZY_COLUMN))

        composeTestRule.onAllNodes(
            hasTestTag(UiTags.HomeScreen.CONTINENT_ITEM)
        ).onFirst().performClick()

        // the waitUntil APIs
        composeTestRule.waitUntilDoesNotExist(hasTestTag(UiTags.DetailsScreen.PROGRESS_INDICATOR))

        composeTestRule.onAllNodes(
            hasTestTag(UiTags.DetailsScreen.COUNTRY_ITEM)
        ).onFirst().assertIsDisplayed()
    }
}