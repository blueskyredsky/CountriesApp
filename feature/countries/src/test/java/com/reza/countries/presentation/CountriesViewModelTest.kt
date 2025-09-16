package com.reza.countries.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.reza.common.domain.model.ResultState
import com.reza.common.util.stringresolver.StringResolver
import com.reza.countries.domain.model.Country
import com.reza.countries.domain.usecase.CountriesUseCase
import com.reza.unit.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CountriesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val countriesUseCase = mockk<CountriesUseCase>()
    private val stringResolver = mockk<StringResolver>()

    private lateinit var viewModel: CountriesViewModel

    @Before
    fun setup() {
        viewModel =
            CountriesViewModel(countriesUseCase = countriesUseCase, stringResolver = stringResolver)
    }

    @Test
    fun countriesViewModel_initialState_is_empty() = runTest {
        assertThat(CountriesUiState.Empty).isEqualTo(viewModel.countriesUiState.value)
    }

    @Test
    fun countriesViewModel_onGetCountries_showsLoadingAndSuccessStates() = runTest {
        // Given
        coEvery { countriesUseCase.getCountries("") } coAnswers {
            delay(1L)
            ResultState.Success(Country.LIST_OF_COUNTRIES)
        }

        // When
        viewModel.onEvent(CountriesEvent.GetCountries(continentCode = ""))

        // Then
        viewModel.countriesUiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState).isInstanceOf(CountriesUiState.Loading::class.java)
            assertThat(loadingState).isNotInstanceOf(CountriesUiState.Refreshing::class.java)

            val successState = awaitItem()
            assertThat(successState).isInstanceOf(CountriesUiState.Success::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun countriesViewModel_onGetCountries_showsLoadingAndErrorStates() = runTest {
        // Given
        coEvery { countriesUseCase.getCountries("") } coAnswers {
            delay(1L)
            ResultState.Failure("")
        }

        // When
        viewModel.onEvent(CountriesEvent.GetCountries(""))

        // Then
        viewModel.countriesUiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState).isInstanceOf(CountriesUiState.Loading::class.java)

            val successState = awaitItem()
            assertThat(successState).isInstanceOf(CountriesUiState.Error::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun countriesViewModel_onConsumeErrorMessage_showsErrorStateWithNullMessage() = runTest {
        // Given

        // When
        viewModel.onEvent(CountriesEvent.ConsumeErrorMessage)

        // Then
        assertThat(CountriesUiState.Error(null)).isEqualTo(viewModel.countriesUiState.value)
    }

    @Test
    fun countriesViewModel_onGetCountries_shouldNotCallApiAgain_whenCountriesUiStateIsAlreadySuccess() =
        runTest {
            // Given
            viewModel.setCountriesUiStateToSuccess(ResultState.Success(Country.LIST_OF_COUNTRIES))

            // When
            viewModel.onEvent(CountriesEvent.GetCountries(""))

            // Then
            viewModel.countriesUiState.test {
                val expectedState = awaitItem()
                assertThat(expectedState).isNotInstanceOf(CountriesUiState.Loading::class.java)
                assertThat(expectedState).isInstanceOf(CountriesUiState.Success::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun countriesViewModel_onGetCountries_shouldCallApiAgain_whenCountriesUiStateIsAlreadyError() =
        runTest {
            // Given
            viewModel.setCountriesUiStateToError(ResultState.Failure(""))
            coEvery { countriesUseCase.getCountries("") } coAnswers {
                delay(1L)
                ResultState.Success(Country.LIST_OF_COUNTRIES)
            }

            // When
            viewModel.onEvent(CountriesEvent.GetCountries(""))

            // Then
            viewModel.countriesUiState.test {
                val loadingState = awaitItem()
                assertThat(loadingState).isInstanceOf(CountriesUiState.Loading::class.java)

                val successState = awaitItem()
                assertThat(successState).isInstanceOf(CountriesUiState.Success::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun countriesViewModel_onGetCountries_shouldCallApiAgain_whenCountriesUiStateIsSuccessAndIsRefreshingIsTrue() =
        runTest {
            // Given
            viewModel.setCountriesUiStateToSuccess(ResultState.Success(Country.LIST_OF_COUNTRIES))
            coEvery { countriesUseCase.getCountries("") } coAnswers {
                delay(1L)
                ResultState.Success(Country.LIST_OF_COUNTRIES)
            }

            // When
            viewModel.onEvent(CountriesEvent.GetCountries(continentCode = "", isRefreshing = true))

            // Then
            viewModel.countriesUiState.test {
                val refreshState = awaitItem()
                assertThat(refreshState).isInstanceOf(CountriesUiState.Refreshing::class.java)
                assertThat(refreshState).isNotInstanceOf(CountriesUiState.Loading::class.java)

                val successState = awaitItem()
                assertThat(successState).isInstanceOf(CountriesUiState.Success::class.java)

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun countriesViewModel_onSearchACountryWhichIsInCountries_shouldReturnFilteredCountry() = runTest {
        // Given
        viewModel.setCountriesUiStateToSuccess(ResultState.Success(Country.LIST_OF_COUNTRIES_WITH_REAL_VALUES))

        // When
        viewModel.onEvent(CountriesEvent.Search("iran"))

        // Then
        viewModel.filteredCountries.test {
            val item = awaitItem()
            assertThat(item).hasSize(1)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun countriesViewModel_onSearchACountryWhichIsNotInCountries_shouldReturnEmptyList() = runTest {
        // Given
        viewModel.setCountriesUiStateToSuccess(ResultState.Success(Country.LIST_OF_COUNTRIES_WITH_REAL_VALUES))

        // When
        viewModel.onEvent(CountriesEvent.Search("Germany"))

        // Then
        viewModel.filteredCountries.test {
            val item = awaitItem()
            assertThat(item).hasSize(0)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun countriesViewModel_onSearchACountry_whenApiIsFailed_shouldReturnEmptyList() = runTest {
        // Given
        viewModel.setCountriesUiStateToError(ResultState.Failure(""))

        // When
        viewModel.onEvent(CountriesEvent.Search("Germany"))

        // Then
        viewModel.filteredCountries.test {
            val item = awaitItem()
            assertThat(item).hasSize(0)

            cancelAndIgnoreRemainingEvents()
        }
    }
}