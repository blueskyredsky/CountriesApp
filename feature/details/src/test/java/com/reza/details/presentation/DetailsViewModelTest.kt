package com.reza.details.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.reza.common.domain.model.ResultState
import com.reza.common.util.stringresolver.StringResolver
import com.reza.details.domain.model.Country
import com.reza.details.domain.usecase.CountriesUseCase
import com.reza.unit.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val countriesUseCase = mockk<CountriesUseCase>()
    private val stringResolver = mockk<StringResolver>()

    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        viewModel =
            DetailsViewModel(countriesUseCase = countriesUseCase, stringResolver = stringResolver)
    }

    @Test
    fun detailsViewModel_initialState_is_empty() = runTest {
        assertThat(DetailsUiState.Empty).isEqualTo(viewModel.detailsUiState.value)
    }

    @Test
    fun detailsViewModel_onGetCountries_showsLoadingAndSuccessStates() = runTest {
        // Given
        coEvery { countriesUseCase.getCountries("") } coAnswers {
            delay(1L)
            ResultState.Success(Country.LIST_OF_COUNTRIES)
        }

        // When
        viewModel.onEvent(DetailsEvent.GetCountries(continentCode = ""))

        // Then
        viewModel.detailsUiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState).isInstanceOf(DetailsUiState.Loading::class.java)
            assertThat(loadingState).isNotInstanceOf(DetailsUiState.Refreshing::class.java)

            val successState = awaitItem()
            assertThat(successState).isInstanceOf(DetailsUiState.Success::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun detailsViewModel_onGetCountries_showsLoadingAndErrorStates() = runTest {
        // Given
        coEvery { countriesUseCase.getCountries("") } coAnswers {
            delay(1L)
            ResultState.Failure("")
        }

        // When
        viewModel.onEvent(DetailsEvent.GetCountries(""))

        // Then
        viewModel.detailsUiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState).isInstanceOf(DetailsUiState.Loading::class.java)

            val successState = awaitItem()
            assertThat(successState).isInstanceOf(DetailsUiState.Error::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun detailsViewModel_onConsumeErrorMessage_showsErrorStateWithNullMessage() = runTest {
        // Given

        // When
        viewModel.onEvent(DetailsEvent.ConsumeErrorMessage)

        // Then
        assertThat(DetailsUiState.Error(null)).isEqualTo(viewModel.detailsUiState.value)
    }

    @Test
    fun detailsViewModel_onGetCountries_shouldNotCallApiAgain_whenHomeUiStateIsAlreadySuccess() =
        runTest {
            // Given
            viewModel.setDetailsUiStateToSuccess(ResultState.Success(Country.LIST_OF_COUNTRIES))

            // When
            viewModel.onEvent(DetailsEvent.GetCountries(""))

            // Then
            viewModel.detailsUiState.test {
                val expectedState = awaitItem()
                assertThat(expectedState).isNotInstanceOf(DetailsUiState.Loading::class.java)
                assertThat(expectedState).isInstanceOf(DetailsUiState.Success::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun detailsViewModel_onGetCountries_shouldCallApiAgain_whenDetailsUiStateIsAlreadyError() =
        runTest {
            // Given
            viewModel.setDetailsUiStateToError(ResultState.Failure(""))
            coEvery { countriesUseCase.getCountries("") } coAnswers {
                delay(1L)
                ResultState.Success(Country.LIST_OF_COUNTRIES)
            }

            // When
            viewModel.onEvent(DetailsEvent.GetCountries(""))

            // Then
            viewModel.detailsUiState.test {
                val loadingState = awaitItem()
                assertThat(loadingState).isInstanceOf(DetailsUiState.Loading::class.java)

                val successState = awaitItem()
                assertThat(successState).isInstanceOf(DetailsUiState.Success::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun detailsViewModel_onGetCountries_shouldCallApiAgain_whenDetailsUiStateIsSuccessAndIsRefreshingIsTrue() =
        runTest {
            // Given
            viewModel.setDetailsUiStateToSuccess(ResultState.Success(Country.LIST_OF_COUNTRIES))
            coEvery { countriesUseCase.getCountries("") } coAnswers {
                delay(1L)
                ResultState.Success(Country.LIST_OF_COUNTRIES)
            }

            // When
            viewModel.onEvent(DetailsEvent.GetCountries(continentCode = "", isRefreshing = true))

            // Then
            viewModel.detailsUiState.test {
                val refreshState = awaitItem()
                assertThat(refreshState).isInstanceOf(DetailsUiState.Refreshing::class.java)
                assertThat(refreshState).isNotInstanceOf(DetailsUiState.Loading::class.java)

                val successState = awaitItem()
                assertThat(successState).isInstanceOf(DetailsUiState.Success::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun detailsViewModel_onSearchACountryWhichIsInCountries_shouldReturnFilteredCountry() = runTest {
        // Given
        viewModel.setDetailsUiStateToSuccess(ResultState.Success(Country.LIST_OF_COUNTRIES_WITH_REAL_VALUES))

        // When
        viewModel.onEvent(DetailsEvent.Search("iran"))

        // Then
        viewModel.filteredCountries.test {
            val item = awaitItem()
            assertThat(item).hasSize(1)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun detailsViewModel_onSearchACountryWhichIsNotInCountries_shouldReturnEmptyList() = runTest {
        // Given
        viewModel.setDetailsUiStateToSuccess(ResultState.Success(Country.LIST_OF_COUNTRIES_WITH_REAL_VALUES))

        // When
        viewModel.onEvent(DetailsEvent.Search("Germany"))

        // Then
        viewModel.filteredCountries.test {
            val item = awaitItem()
            assertThat(item).hasSize(0)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun detailsViewModel_onSearchACountry_whenApiIsFailed_shouldReturnEmptyList() = runTest {
        // Given
        viewModel.setDetailsUiStateToError(ResultState.Failure(""))

        // When
        viewModel.onEvent(DetailsEvent.Search("Germany"))

        // Then
        viewModel.filteredCountries.test {
            val item = awaitItem()
            assertThat(item).hasSize(0)

            cancelAndIgnoreRemainingEvents()
        }
    }
}