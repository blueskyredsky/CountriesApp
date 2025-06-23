package com.reza.feature.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(HomeModule::class)
@RunWith(AndroidJUnit4::class)
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

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        hiltRule.inject()
        Dispatchers.setMain(testDispatcher)

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

        composeTestRule.setContent {
            ContinentsScreen(
                viewModel = homeViewModel,
                onSelectContinent = {}
            )
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun screen_displays_loading_state() = runTest(testDispatcher) {
        // By default, your ViewModel should start in a loading state, and your use case
        // won't return data immediately. The initial LaunchedEffect in ContinentsScreen
        // will call viewModel.onEvent(HomeEvent.GetContinents()) which leads to loading.
        advanceUntilIdle() // Allow the LaunchedEffect to trigger and ViewModel to process

        composeTestRule.onNodeWithTag(UiTags.HomeScreen.SHIMMER_LAZY_COLUMN).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.continents)).assertIsDisplayed()
    }
}