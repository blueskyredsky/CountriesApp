package com.reza.feature.home.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent
import com.reza.feature.home.domain.usecase.ContinentImageUseCase
import com.reza.feature.home.domain.usecase.ContinentsUseCase
import com.reza.feature.home.domain.usecase.TestContinentImageUseCase
import com.reza.feature.home.domain.usecase.TestContinentUseCase
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

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(
            continentsUseCase = continentsUseCase,
            continentsImageUseCase = continentImageUseCase
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
}