package com.reza.countriesapp

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.testing.TestNavHostController
import com.reza.countriesapp.presentation.MainActivity
import com.reza.systemdesign.ui.util.Constants
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

    private lateinit var navController: TestNavHostController

    /*@Before
    fun setupAppNavHost() {
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MainScreen(navController = navController)
        }
    }*/

    @Test
    fun appNavHost_verifyStartDestination() {
        /*if (this::navController.isInitialized) {
            val route = navController.currentDestination?.route
            val expectedRoute = composeTestRule.activity.getString(R.string.home_screen_route)
            Truth.assertThat(route).isEqualTo(expectedRoute)
        }*/
        composeTestRule.onNodeWithTag(Constants.UiTags.HomeScreen.customName).assertExists()
    }

    /*@OptIn(ExperimentalTestApi::class)
    @Test
    fun verify_loadingAndThenItemsAreDisplayed() {
        composeTestRule.onNodeWithTag(Constants.UiTags.ProgressIndicator.customName).assertIsDisplayed()

        // the waitUntil APIs
        composeTestRule.waitUntilDoesNotExist(hasTestTag(Constants.UiTags.ProgressIndicator.customName))

        composeTestRule.onAllNodes(
            hasTestTag(Constants.UiTags.ContinentItem.customName)
        ).onFirst().assertIsDisplayed()
    }*/

    /*@OptIn(ExperimentalTestApi::class)
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
    }*/

    /*@OptIn(ExperimentalTestApi::class)
    @Test
    fun verify_itemsOnDetailsScreenAreDisplayed() {
        // the waitUntil APIs
        composeTestRule.waitUntilDoesNotExist(hasTestTag(Constants.UiTags.ProgressIndicator.customName))

        composeTestRule.onAllNodes(
            hasTestTag(Constants.UiTags.ContinentItem.customName)
        ).onFirst().performClick()

        // the waitUntil APIs
        composeTestRule.waitUntilDoesNotExist(hasTestTag(Constants.UiTags.ProgressIndicator.customName))

        composeTestRule.onAllNodes(
            hasTestTag(Constants.UiTags.CountryItem.customName)
        ).onFirst().assertIsDisplayed()
    }*/
}