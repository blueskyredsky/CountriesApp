package com.reza.feature.continents.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.reza.common.domain.model.ResultState
import com.reza.common.util.stringresolver.StringResolver
import com.reza.feature.continents.domain.model.Continent
import com.reza.feature.continents.domain.usecase.ContinentImageUseCase
import com.reza.feature.continents.domain.usecase.ContinentsUseCase
import com.reza.unit.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ContinentsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val continentsUseCase = mockk<ContinentsUseCase>()
    private val continentImageUseCase = mockk<ContinentImageUseCase>()
    private val stringResolver = mockk<StringResolver>()

    private lateinit var viewModel: ContinentsViewModel

    @Before
    fun setup() {
        viewModel = ContinentsViewModel(
            continentsUseCase = continentsUseCase,
            continentsImageUseCase = continentImageUseCase,
            stringResolver = stringResolver
        )
    }

    @Test
    fun continentsViewModel_initialState_is_empty() = runTest {
        assertThat(ContinentsUiState.Empty).isEqualTo(viewModel.continentsUiState.value)
    }

    @Test
    fun continentsViewModel_onGetContinents_showsLoadingAndSuccessStates() = runTest {
        // Given
        coEvery { continentsUseCase.getContinents() } coAnswers {
            delay(1L)
            ResultState.Success(Continent.LIST_OF_CONTINENTS)
        }
        every { continentImageUseCase.findContinentImage(any<String>()) } answers { -1 }

        // When
        viewModel.onEvent(ContinentsEvent.GetContinents())

        // Then
        viewModel.continentsUiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState).isInstanceOf(ContinentsUiState.Loading::class.java)
            assertThat(loadingState).isNotInstanceOf(ContinentsUiState.Refreshing::class.java)

            val successState = awaitItem()
            assertThat(successState).isInstanceOf(ContinentsUiState.Success::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun continentsViewModel_onGetContinents_showsLoadingAndErrorStates() = runTest {
        // Given
        coEvery { continentsUseCase.getContinents() } coAnswers {
            delay(1L)
            ResultState.Failure("")
        }
        every { continentImageUseCase.findContinentImage(any<String>()) } answers { -1 }

        // When
        viewModel.onEvent(ContinentsEvent.GetContinents())

        // Then
        viewModel.continentsUiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState).isInstanceOf(ContinentsUiState.Loading::class.java)

            val successState = awaitItem()
            assertThat(successState).isInstanceOf(ContinentsUiState.Error::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun continentsViewModel_onConsumeErrorMessage_showsErrorStateWithNullMessage() = runTest {
        // Given

        // When
        viewModel.onEvent(ContinentsEvent.ConsumeErrorMessage)

        // Then
        assertThat(ContinentsUiState.Error(null)).isEqualTo(viewModel.continentsUiState.value)
    }

    @Test
    fun continentsViewModel_onGetContinents_shouldNotCallApiAgain_whenContinentsUiStateIsAlreadySuccess() =
        runTest {
            // Given
            every { continentImageUseCase.findContinentImage(any<String>()) } answers { -1 }
            viewModel.setContinentsUiStateToSuccess(ResultState.Success(Continent.LIST_OF_CONTINENTS))

            // When
            viewModel.onEvent(ContinentsEvent.GetContinents())

            // Then
            viewModel.continentsUiState.test {
                val expectedState = awaitItem()
                assertThat(expectedState).isNotInstanceOf(ContinentsUiState.Loading::class.java)
                assertThat(expectedState).isInstanceOf(ContinentsUiState.Success::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun continentsViewModel_onGetContinents_shouldCallApiAgain_whenContinentsUiStateIsAlreadyError() =
        runTest {
            // Given
            coEvery { continentsUseCase.getContinents() } coAnswers {
                delay(1L)
                ResultState.Success(Continent.LIST_OF_CONTINENTS)
            }
            every { continentImageUseCase.findContinentImage(any<String>()) } answers { -1 }
            viewModel.setContinentsUiStateToError(ResultState.Failure(""))

            // When
            viewModel.onEvent(ContinentsEvent.GetContinents())

            // Then
            viewModel.continentsUiState.test {
                val loadingState = awaitItem()
                assertThat(loadingState).isInstanceOf(ContinentsUiState.Loading::class.java)

                val successState = awaitItem()
                assertThat(successState).isInstanceOf(ContinentsUiState.Success::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun continentsViewModel_onGetContinents_shouldCallApiAgain_whenContinentsUiStateIsSuccessAndIsRefreshingIsTrue() =
        runTest {
            // Given
            coEvery { continentsUseCase.getContinents() } coAnswers {
                delay(1L)
                ResultState.Success(Continent.LIST_OF_CONTINENTS)
            }
            every { continentImageUseCase.findContinentImage(any<String>()) } answers { -1 }
            viewModel.setContinentsUiStateToSuccess(ResultState.Success(Continent.LIST_OF_CONTINENTS))

            // When
            viewModel.onEvent(ContinentsEvent.GetContinents(isRefreshing = true))

            // Then
            viewModel.continentsUiState.test {
                val refreshState = awaitItem()
                assertThat(refreshState).isInstanceOf(ContinentsUiState.Refreshing::class.java)
                assertThat(refreshState).isNotInstanceOf(ContinentsUiState.Loading::class.java)

                val successState = awaitItem()
                assertThat(successState).isInstanceOf(ContinentsUiState.Success::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }
}