package com.reza.countriesapp

import androidx.activity.compose.setContent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.factory.ContinentFactory
import com.reza.countriesapp.presentation.MainActivity
import com.reza.countriesapp.presentation.continents.ContinentItem
import com.reza.countriesapp.presentation.continents.ContinentList
import com.reza.countriesapp.presentation.continents.ContinentsScreen
import com.reza.countriesapp.util.Constants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ContinentsScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    private val continents = ContinentFactory.createContinents()

    @Test
    fun continent_item_is_displayed() {
        val continent = Continent.DEFAULT_CONTINENT
        composeTestRule.activity.setContent {
            ContinentItem(item = continent) {}
        }

        composeTestRule.onNodeWithText(continent.name!!).assertIsDisplayed()
    }

    @Test
    fun first_item_in_continent_list_is_displayed() {
        composeTestRule.activity.setContent {
            ContinentList(isRefreshing = false, continents = continents, onSelectContinent = {}) {}
        }

        composeTestRule.onAllNodes(
            hasTestTag(Constants.UiTags.ContinentItem.customName)
        ).onFirst().assertTextEquals(continents.first().name!!)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun click_on_first_item() {
        composeTestRule.activity.setContent {
            ContinentsScreen {}
        }

        composeTestRule.waitUntilDoesNotExist(hasTestTag(Constants.UiTags.ProgressIndicator.customName), timeoutMillis = 2000L)

        composeTestRule.onAllNodes(
            hasTestTag(Constants.UiTags.ContinentItem.customName)
        ).onFirst().performClick()

        //composeTestRule.waitUntilDoesNotExist(hasTestTag(Constants.UiTags.LazyColumn.customName))

        composeTestRule.onNodeWithText(continents.first().code!!).assertIsDisplayed()
    }
}