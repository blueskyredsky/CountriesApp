package com.reza.countriesapp

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.google.common.truth.Truth
import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.factory.ContinentFactory
import com.reza.countriesapp.presentation.MainActivity
import com.reza.countriesapp.presentation.MainScreen
import com.reza.countriesapp.presentation.continents.ContinentItem
import com.reza.countriesapp.util.Constants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ContinentsScreenTest {

    /**
     * This rule is because MainActivity scoped with AndroidEntryPoint
     */
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MainScreen(navController = navController)
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun verify_loadingAndThenItemsAreDisplayed() {
        composeTestRule.onNodeWithTag(Constants.UiTags.ProgressIndicator.customName).assertIsDisplayed()

        // the waitUntil APIs
        composeTestRule.waitUntilDoesNotExist(hasTestTag(Constants.UiTags.ProgressIndicator.customName))

        composeTestRule.onAllNodes(
            hasTestTag(Constants.UiTags.ContinentItem.customName)
        ).onFirst().assertIsDisplayed()
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        if (this::navController.isInitialized) {
            val route = navController.currentDestination?.route
            val expectedRoute = composeTestRule.activity.getString(R.string.home_screen_route)
            Truth.assertThat(route).isEqualTo(expectedRoute)
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun appNavHost_verifyNavigateToDetailsDestination() {
        // the waitUntil APIs
        composeTestRule.waitUntilDoesNotExist(hasTestTag(Constants.UiTags.ProgressIndicator.customName))

        composeTestRule.onAllNodes(
            hasTestTag(Constants.UiTags.ContinentItem.customName)
        ).onFirst().performClick()

        val route = navController.currentDestination?.route
        val expectedRoute = composeTestRule.activity.getString(R.string.details_screen_route)
        Truth.assertThat(route).isEqualTo(expectedRoute)
    }

    // continue testing details screen
}