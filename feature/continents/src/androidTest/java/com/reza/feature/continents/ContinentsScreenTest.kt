package com.reza.feature.continents

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
import com.reza.feature.continents.di.ContinentsModule
import com.reza.feature.continents.domain.model.Continent
import com.reza.feature.continents.domain.usecase.FakeContinentImageUseCase
import com.reza.feature.continents.domain.usecase.FakeContinentsUseCase
import com.reza.feature.continents.presentation.ContinentsScreen
import com.reza.feature.continents.presentation.ContinentsViewModel
import com.reza.systemdesign.ui.util.UiTags
import com.reza.ui.util.FakeStringResolver
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(ContinentsModule::class)
class ContinentsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Inject
    lateinit var fakeStringResolver: FakeStringResolver

    @Inject
    lateinit var fakeContinentsUseCase: FakeContinentsUseCase

    @Inject
    lateinit var fakeContinentImageUseCase: FakeContinentImageUseCase

    private lateinit var continentsViewModel: ContinentsViewModel

    @Before
    fun setup() {
        hiltRule.inject()

        // Reset the state of your fakes before each test to ensure isolation
        fakeContinentsUseCase.reset()
        fakeContinentImageUseCase.clearMappings() // Re-initialize default mappings if necessary
        // Re-add default mappings if your FakeContinentImageUseCase does not re-add them on clear
        fakeContinentImageUseCase.setContinentImage(Continent.ASIA, R.drawable.ic_asia)
        fakeContinentImageUseCase.setContinentImage(Continent.EUROPE, R.drawable.ic_europe)
        fakeContinentImageUseCase.setContinentImage(Continent.AFRICA, R.drawable.ic_africa)
        fakeContinentImageUseCase.setContinentImage(Continent.NORTH_AMERICA, R.drawable.ic_north_america)
        fakeContinentImageUseCase.setContinentImage(Continent.SOUTH_AMERICA, R.drawable.ic_south_america)
        fakeContinentImageUseCase.setContinentImage(Continent.OCEANIA, R.drawable.ic_australia)
        fakeContinentImageUseCase.setContinentImage(Continent.ANTARCTICA, R.drawable.ic_antarctica)


        // Manually create the ContinentViewModel using the injected fakes
        continentsViewModel = ContinentsViewModel(
            continentsUseCase = fakeContinentsUseCase,
            continentsImageUseCase = fakeContinentImageUseCase,
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
            ContinentsScreen(
                viewModel = continentsViewModel,
                onSelectContinent = {}
            )
        }

        composeTestRule.onAllNodes(
            hasTestTag(UiTags.ContinentScreen.CONTINENT_ITEM)
        ).onFirst().assertIsDisplayed()
    }

    @Test
    fun screen_displays_error_state() = runTest {
        val errorMessage = "Failed to load continents"
        fakeContinentsUseCase.setError(IOException(errorMessage))

        composeTestRule.setContent {
            ContinentsScreen(
                viewModel = continentsViewModel,
                onSelectContinent = {}
            )
        }

        composeTestRule.onNode(
            hasText(errorMessage) and hasAnyAncestor(hasTestTag(UiTags.Common.SNACK_BAR))
        ).assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun screen_displays_error_state_and_retries_successfully() = runTest {
        val errorMessage = "Failed to load continents"
        fakeContinentsUseCase.setError(IOException(errorMessage))

        composeTestRule.setContent {
            ContinentsScreen(
                viewModel = continentsViewModel,
                onSelectContinent = {}
            )
        }

        composeTestRule.onNode(
            hasText(errorMessage) and hasAnyAncestor(hasTestTag(UiTags.Common.SNACK_BAR))
        ).assertIsDisplayed()

        // Trigger the retry action by clicking the "Retry" button
        fakeContinentsUseCase.setSuccess()
        composeTestRule.onNode(hasText("Retry")).performClick()

        composeTestRule.onAllNodes(
            hasTestTag(UiTags.ContinentScreen.CONTINENT_ITEM)
        ).onFirst().assertIsDisplayed()
    }
}