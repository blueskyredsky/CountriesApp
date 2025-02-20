package com.reza.feature.home.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.reza.common.domain.model.ResultState
import com.reza.common.util.stringresolver.StringResolver
import com.reza.feature.home.domain.model.Continent
import com.reza.feature.home.domain.usecase.ContinentImageUseCase
import com.reza.feature.home.domain.usecase.ContinentsUseCase
import com.reza.unit.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val continentsUseCase = mockk<ContinentsUseCase>()
    private val continentImageUseCase = mockk<ContinentImageUseCase>()
    private val stringResolver = mockk<StringResolver>()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(
            continentsUseCase = continentsUseCase,
            continentsImageUseCase = continentImageUseCase,
            stringResolver = stringResolver
        )
    }

    @Test
    fun homeViewModel_initialState_is_emptyAndIdle() = runTest {
        assertThat(HomeUiState.Empty).isEqualTo(viewModel.homeUiState.value)
        assertThat(HomeLoadingState.Idle).isEqualTo(viewModel.homeLoadingState.value)
    }

    @Test
    fun homeViewModel_onGetContinents_showsLoadingAndSuccessStates() = runTest {
        // Given
        coEvery { continentsUseCase.getContinents() } coAnswers {
            delay(1L)
            ResultState.Success(Continent.LIST_OF_CONTINENTS)
        }
        every { continentImageUseCase.findContinentImage(any<String>()) } answers { -1 }

        // When
        viewModel.onEvent(HomeEvent.GetContinents())

        // Then
        viewModel.homeUiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState).isInstanceOf(HomeUiState.Loading::class.java)

            val successState = awaitItem()
            assertThat(successState).isInstanceOf(HomeUiState.Success::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun homeViewModel_onGetContinents_showsLoadingAndErrorStates() = runTest {
        // Given
        coEvery { continentsUseCase.getContinents() } coAnswers {
            delay(1L)
            ResultState.Failure("")
        }
        every { continentImageUseCase.findContinentImage(any<String>()) } answers { -1 }

        // When
        viewModel.onEvent(HomeEvent.GetContinents())

        // Then
        viewModel.homeUiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState).isInstanceOf(HomeUiState.Loading::class.java)

            val successState = awaitItem()
            assertThat(successState).isInstanceOf(HomeUiState.Error::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun homeViewModel_onGetContinents_showsLoadingAndIdleStates_whenIsNotRefreshing() = runTest {
        // Given
        coEvery { continentsUseCase.getContinents() } coAnswers {
            delay(1L)
            ResultState.Success(Continent.LIST_OF_CONTINENTS)
        }
        every { continentImageUseCase.findContinentImage(any<String>()) } answers { -1 }

        // When
        viewModel.onEvent(HomeEvent.GetContinents())

        // Them
        viewModel.homeLoadingState.test {
            val loadingState = awaitItem()
            assertThat(loadingState).isEqualTo(HomeLoadingState.Loading)

            val idleState = awaitItem()
            assertThat(idleState).isEqualTo(HomeLoadingState.Idle)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun homeViewModel_onGetContinents_showsRefreshingAndIdleStates_whenIsRefreshing() = runTest {
        // Given
        coEvery { continentsUseCase.getContinents() } coAnswers {
            delay(1L)
            ResultState.Success(Continent.LIST_OF_CONTINENTS)
        }
        every { continentImageUseCase.findContinentImage(any<String>()) } answers { -1 }

        // When
        viewModel.onEvent(HomeEvent.GetContinents(true))

        // Then
        viewModel.homeLoadingState.test {
            val loadingState = awaitItem()
            assertThat(loadingState).isEqualTo(HomeLoadingState.Refreshing)

            val idleState = awaitItem()
            assertThat(idleState).isEqualTo(HomeLoadingState.Idle)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun homeViewModel_onConsumeErrorMessage_showsErrorStateWithNullMessage() = runTest {
        // Given

        // When
        viewModel.onEvent(HomeEvent.ConsumeErrorMessage)

        // Then
        assertThat(HomeUiState.Error(null)).isEqualTo(viewModel.homeUiState.value)
    }

    @Test
    fun homeViewModel_onGetContinents_shouldNotCallApiAgain_whenHomeUiStateIsAlreadySuccess() =
        runTest {
            // Given
            every { continentImageUseCase.findContinentImage(any<String>()) } answers { -1 }
            viewModel.setHomeUiStateToSuccess(ResultState.Success(Continent.LIST_OF_CONTINENTS))

            // When
            viewModel.onEvent(HomeEvent.GetContinents())

            // Then
            viewModel.homeUiState.test {
                val expectedState = awaitItem()
                assertThat(expectedState).isNotInstanceOf(HomeUiState.Loading::class.java)
                assertThat(expectedState).isInstanceOf(HomeUiState.Success::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }
}