package com.reza.feature.countries

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.reza.countries.di.DetailsModule
import com.reza.feature.countries.domain.usecases.FakeCountriesUseCase
import com.reza.countries.presentation.CountriesScreen
import com.reza.countries.presentation.DetailsViewModel
import com.reza.systemdesign.ui.util.UiTags
import com.reza.ui.util.FakeStringResolver
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(DetailsModule::class)
class CountriesScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Inject
    lateinit var fakeStringResolver: FakeStringResolver

    @Inject
    lateinit var fakeCountriesUseCase: FakeCountriesUseCase

    private lateinit var detailsViewModel: DetailsViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        detailsViewModel = DetailsViewModel(
            countriesUseCase = fakeCountriesUseCase,
            stringResolver = fakeStringResolver
        )
    }

    @After
    fun tearDown() {
        /* ٔNO-OP */
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun screen_displays_success_state() = runTest {
        composeTestRule.setContent {
            CountriesScreen(
                viewModel = detailsViewModel,
                onBackClick = {},
                continentCode = "NA",
                continent = "North America",
            )
        }

        composeTestRule.onAllNodes(
            hasTestTag(UiTags.DetailsScreen.COUNTRY_ITEM)
        ).onFirst().assertIsDisplayed()
    }

    @Test
    fun screen_displays_error_state() = runTest {
        fakeCountriesUseCase.shouldReturnError = true

        composeTestRule.setContent {
            CountriesScreen(
                viewModel = detailsViewModel,
                onBackClick = {},
                continentCode = "NA",
                continent = "North America",
            )
        }

        composeTestRule.onNode(
            hasText(FakeCountriesUseCase.ERROR_MESSAGE) and hasAnyAncestor(hasTestTag(UiTags.Common.SNACK_BAR))
        ).assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun screen_displays_error_state_and_retries_successfully() = runTest {
        fakeCountriesUseCase.shouldReturnError = true

        composeTestRule.setContent {
            CountriesScreen(
                viewModel = detailsViewModel,
                onBackClick = {},
                continentCode = "NA",
                continent = "North America",
            )
        }

        composeTestRule.onNode(
            hasText(FakeCountriesUseCase.ERROR_MESSAGE) and hasAnyAncestor(hasTestTag(UiTags.Common.SNACK_BAR))
        ).assertIsDisplayed()

        // Trigger the retry action by clicking the "Retry" button
        fakeCountriesUseCase.shouldReturnError = false
        composeTestRule.onNode(hasText("Retry")).performClick()

        composeTestRule.onAllNodes(
            hasTestTag(UiTags.DetailsScreen.COUNTRY_ITEM)
        ).onFirst().assertIsDisplayed()
    }
}