package com.reza.countriesapp

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
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
import com.reza.countriesapp.presentation.continents.ContinentList
import com.reza.countriesapp.presentation.continents.ContinentsScreen
import com.reza.countriesapp.util.Constants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ContinentsScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    private lateinit var navController: TestNavHostController

    private val continents = ContinentFactory.createContinents()

    @Before
    fun setupAppNavHost() {
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MainScreen(navController = navController)
        }
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule.onNodeWithTag(Constants.UiTags.ProgressIndicator.customName).assertIsDisplayed()
    }

    /*@Test
    fun continent_item_is_displayed() {
        val continent = Continent.DEFAULT_CONTINENT
        composeTestRule.activity.setContent {
            ContinentItem(item = continent) {}
        }

        composeTestRule.onNodeWithText(continent.name!!).assertIsDisplayed()
    }*/

    /*@Test
    fun first_item_in_continent_list_is_displayed() {
        composeTestRule.activity.setContent {
            ContinentList(isRefreshing = false, continents = continents, onSelectContinent = {}) {}
        }

        composeTestRule.onAllNodes(
            hasTestTag(Constants.UiTags.ContinentItem.customName)
        ).onFirst().assertTextEquals(continents.first().name!!)
    }*/

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun click_on_first_item() {
        // the waitUntil APIs
        composeTestRule.waitUntilDoesNotExist(hasTestTag(Constants.UiTags.ProgressIndicator.customName))

        composeTestRule.onAllNodes(
            hasTestTag(Constants.UiTags.ContinentItem.customName)
        ).onFirst().performClick()

        val route = navController.currentDestination?.route
        Truth.assertThat(route).isEqualTo("detail/{continentCode}")
    }
}