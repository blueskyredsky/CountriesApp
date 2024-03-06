package com.reza.countriesapp.presentation.home

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.usecase.FakeContinentsUseCase
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeContinentsUseCase: FakeContinentsUseCase
    private lateinit var viewModel: HomeViewModel


    @Before
    fun setup() {
        fakeContinentsUseCase = FakeContinentsUseCase(testDispatcher)
        viewModel = HomeViewModel(
            continentsUseCase = fakeContinentsUseCase,
            mainDispatcher = testDispatcher
        )
    }

    @Test
    fun `testing default values of ui state`() {
        Truth.assertThat(viewModel.continentsState.value.continents)
            .isEqualTo(emptyList<Continent>())
        Truth.assertThat(viewModel.continentsState.value.errorMessage).isNull()
        Truth.assertThat(viewModel.continentsState.value.isLoading).isFalse()
    }

    @Test
    fun `should return list of continents if successful`() = runTest(testDispatcher.scheduler) {
        // Then
        viewModel.continentsState.test {
            Truth.assertThat(
                HomeState(
                    isLoading = true,
                    continents = emptyList(),
                    errorMessage = null
                )
            ).isEqualTo(awaitItem())

            Truth.assertThat(
                HomeState(
                    isLoading = false,
                    continents = Continent.LIST_OF_CONTINENTS,
                    errorMessage = null
                )
            ).isEqualTo(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should return error if not successful`() = runTest(testDispatcher.scheduler) {
        // Given
        fakeContinentsUseCase.setSuccessful(false)

        // Then
        viewModel.continentsState.test {
            Truth.assertThat(
                HomeState(
                    isLoading = true,
                    continents = emptyList(),
                    errorMessage = null
                )
            ).isEqualTo(awaitItem())

            Truth.assertThat(
                HomeState(
                    isLoading = false,
                    continents = emptyList(),
                    errorMessage = ""
                )
            ).isEqualTo(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}