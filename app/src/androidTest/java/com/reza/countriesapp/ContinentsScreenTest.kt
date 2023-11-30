package com.reza.countriesapp

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.factory.ContinentFactory
import com.reza.countriesapp.presentation.MainActivity
import com.reza.countriesapp.presentation.common.LoadingItem
import com.reza.countriesapp.presentation.continents.ContinentItem
import com.reza.countriesapp.presentation.continents.ContinentList
import com.reza.countriesapp.util.Constants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
class ContinentsScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

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
        val continents = ContinentFactory.createContinents()
        composeTestRule.activity.setContent {
            ContinentList(isRefreshing = false, continents = continents, onSelectContinent = {}) {}
        }

        composeTestRule.onAllNodes(
            hasTestTag(Constants.UiTags.ContinentItem.customName)
        ).onFirst().assertTextEquals(continents.first().name!!)
    }
}