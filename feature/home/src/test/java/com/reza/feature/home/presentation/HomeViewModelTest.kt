package com.reza.feature.home.presentation


import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.whenever
import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent
import com.reza.feature.home.domain.usecase.ContinentImageUseCase
import com.reza.feature.home.domain.usecase.ContinentsUseCase
import com.reza.unit.util.MainDispatcherRule
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var continentImageUseCase: ContinentImageUseCase

    @Mock
    private lateinit var continentsUseCase: ContinentsUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(
            continentsUseCase = continentsUseCase,
            continentsImageUseCase = continentImageUseCase
        )
    }

    @Test
    fun `check initial states`() = runTest {
        assertThat(HomeUiState.Empty).isEqualTo(viewModel.homeUiState.value)
        assertThat(HomeLoadingState.Idle).isEqualTo(viewModel.homeLoadingState.value)
    }

    @Test
    fun testLoadingAndSuccessStates() = runTest {
        // Given
        val expectedContinents = Continent.LIST_OF_CONTINENTS
        whenever(continentsUseCase.getContinents()).thenReturn(ResultState.Success(expectedContinents))

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
}