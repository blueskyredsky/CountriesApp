package com.reza.countriesapp.presentation.continents

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth
import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.usecase.ContinentsUseCase
import com.reza.countriesapp.domain.usecase.CountriesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class ContinentsViewModelTest {

    @Mock
    private lateinit var continentsUseCase: ContinentsUseCase

    @Mock
    private lateinit var countriesUseCase: CountriesUseCase

    private val testDispatcher = StandardTestDispatcher()
    private val savedStateHandle = SavedStateHandle()
    private lateinit var viewModel: ContinentsViewModel

    @Before
    fun setup() {
        viewModel = ContinentsViewModel(
            savedStateHandle = savedStateHandle,
            continentsUseCase = continentsUseCase,
            countriesUseCase = countriesUseCase,
            mainDispatcher = testDispatcher
        )
    }

    @After
    fun teardown() {

    }

    @Test
    fun `testing default values of ui state`() {
        // Then
        Truth.assertThat(viewModel.continentsState.value.continents)
            .isEqualTo(emptyList<Continent>())
        Truth.assertThat(viewModel.continentsState.value.selectedContinent).isNull()
        Truth.assertThat(viewModel.continentsState.value.errorMessage).isNull()
        Truth.assertThat(viewModel.continentsState.value.isLoading).isFalse()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return list of continents if successful`() = runTest(testDispatcher.scheduler) {
        // Given
        val listOfContinents = listOf(Continent("Africa", "AF"), Continent("Asia", "AS"))
        whenever(continentsUseCase.getContinents()).thenReturn(listOfContinents)

        // When
        viewModel.onEvent(ContinentsEvent.RequestContinents)
        advanceUntilIdle()

        // Then
        viewModel.continentsState.test {
            val result = awaitItem()
            Truth.assertThat(
                ContinentsState(
                    isLoading = true,
                    continents = emptyList(),
                    errorMessage = null,
                    selectedContinent = null
                )
            ).isEqualTo(result)
//            Truth.assertThat(
//                ContinentsState(
//                    isLoading = false,
//                    continents = listOfContinents,
//                    errorMessage = null,
//                    selectedContinent = null
//                )
//            ).isEqualTo(expectMostRecentItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `t`() = runTest {
        val flow = MutableStateFlow<String>("")
        flow.value = "1"
        flow.value = "2"
        flow.test {
            assertEquals("1", awaitItem())
            assertEquals("2", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}