package com.reza.feature.home.presentation


import androidx.lifecycle.viewModelScope
import app.cash.turbine.test
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.whenever
import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent
import com.reza.feature.home.domain.usecase.ContinentImageUseCase
import com.reza.feature.home.domain.usecase.ContinentsUseCase
import com.reza.unit.util.MainDispatcherRule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
    fun checkInitialStates() = runTest {
        Truth.assertThat(HomeUiState.Empty).isEqualTo(viewModel.homeUiState.value)
        Truth.assertThat(HomeLoadingState.Idle).isEqualTo(viewModel.homeLoadingState.value)
    }

    @Test
    fun testLoadingAndSuccessStates() = runTest {
        // Given
        val testDispatcher = UnconfinedTestDispatcher(testScheduler) // Pass testScheduler

        whenever(continentsUseCase.getContinents()).thenReturn(ResultState.Success(Continent.LIST_OF_CONTINENTS))

        backgroundScope.launch(testDispatcher) { // Launch using testDispatcher
            viewModel.onEvent(HomeEvent.GetContinents())
        }

        viewModel.homeUiState.test {
            val loadingState = awaitItem()
            Truth.assertThat(loadingState).isInstanceOf(HomeUiState.Loading::class.java)

            val successState = awaitItem()
            Truth.assertThat(successState).isInstanceOf(HomeUiState.Success::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }
}