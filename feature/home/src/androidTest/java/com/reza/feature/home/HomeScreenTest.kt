package com.reza.feature.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onFirst
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.reza.feature.home.di.HomeModule
import com.reza.feature.home.domain.model.Continent
import com.reza.feature.home.domain.usecase.FakeContinentImageUseCase
import com.reza.feature.home.domain.usecase.FakeContinentsUseCase
import com.reza.feature.home.presentation.ContinentsScreen
import com.reza.feature.home.presentation.HomeViewModel
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
@UninstallModules(HomeModule::class)
class HomeScreenTest {

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

    private lateinit var homeViewModel: HomeViewModel

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


        // Manually create the HomeViewModel using the injected fakes
        homeViewModel = HomeViewModel(
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
    fun screen_displays_loading_state() = runTest {
        composeTestRule.setContent {
            ContinentsScreen(
                viewModel = homeViewModel,
                onSelectContinent = {}
            )
        }

        // Advance the coroutine dispatcher to ensure all coroutines complete
        testScheduler.advanceUntilIdle()

        composeTestRule.onAllNodes(
            hasTestTag(UiTags.HomeScreen.CONTINENT_ITEM)
        ).onFirst().assertIsDisplayed()
    }
}