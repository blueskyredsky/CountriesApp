package com.reza.countriesapp.presentation.details

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth
import com.reza.countriesapp.domain.model.Country
import com.reza.countriesapp.domain.usecase.countries.FakeCountriesUseCase
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeCountriesUseCase: FakeCountriesUseCase
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        fakeCountriesUseCase = FakeCountriesUseCase(testDispatcher)
        viewModel = DetailsViewModel(
            countriesUseCase = fakeCountriesUseCase,
            mainDispatcher = testDispatcher,
            savedStateHandle = SavedStateHandle()
        )
    }

    @Test
    fun `testing default values of ui state`() {
        Truth.assertThat(viewModel.countriesState.value.countries).isNull()
        Truth.assertThat(viewModel.countriesState.value.errorMessage).isNull()
        Truth.assertThat(viewModel.countriesState.value.isLoading).isFalse()
    }

    @Test
    fun `should return list of countries if successful`() = runTest(testDispatcher.scheduler) {
        // Then
        viewModel.countriesState.test {
            Truth.assertThat(
                DetailsState(
                    isLoading = true,
                    countries = null,
                    errorMessage = null
                )
            ).isEqualTo(awaitItem())

            Truth.assertThat(
                DetailsState(
                    isLoading = false,
                    countries = Country.LIST_OF_COUNTRIES,
                    errorMessage = null
                )
            ).isEqualTo(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should return error if not successful`() = runTest(testDispatcher.scheduler) {
        // Given
        fakeCountriesUseCase.setSuccessful(false)

        // Then
        viewModel.countriesState.test {
            Truth.assertThat(
                DetailsState(
                    isLoading = true,
                    countries = null,
                    errorMessage = null
                )
            ).isEqualTo(awaitItem())

            Truth.assertThat(
                DetailsState(
                    isLoading = false,
                    countries = null,
                    errorMessage = ""
                )
            ).isEqualTo(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}