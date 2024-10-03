package com.reza.feature.home.presentation


import app.cash.turbine.test
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.any
import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent
import com.reza.feature.home.domain.usecase.ContinentImageUseCase
import com.reza.feature.home.domain.usecase.ContinentsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @Mock
    private lateinit var continentImageUseCase: ContinentImageUseCase

    @Mock
    private lateinit var continentsUseCase: ContinentsUseCase

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(
            continentsUseCase = continentsUseCase,
            mainDispatcher = testDispatcher,
            continentsImageUseCase = continentImageUseCase
        )
    }

    @Test
    fun `testing default values of ui state`() {
        Truth.assertThat(viewModel.homeState.value.continents).isEqualTo(emptyList<Continent>())
        Truth.assertThat(viewModel.homeState.value.errorMessage).isNull()
        Truth.assertThat(viewModel.homeState.value.isLoading).isFalse()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return list of continents if successful`() = runTest(testDispatcher.scheduler) {
        // Given
        Mockito.`when`(continentImageUseCase.findContinentImage(any())).thenReturn(1)
        Mockito.`when`(continentsUseCase.getContinents()).thenReturn(ResultState.Success(Continent.LIST_OF_CONTINENTS))

        // Then
        /*viewModel.homeState.test {
            val item1 = awaitItem()
            Truth.assertThat(
                HomeState(
                    isLoading = true,
                    continents = emptyList(),
                    errorMessage = null
                )
            ).isEqualTo(item1)

            val item2 = awaitItem()
            Truth.assertThat(
                HomeState(
                    isLoading = false,
                    continents = Continent.LIST_OF_CONTINENTS.map {
                        ContinentView(
                            continent = it,
                            imageResource = continentImageUseCase.findContinentImage(it.name)
                        )
                    },
                    errorMessage = null
                )
            ).isEqualTo(item2)
            //cancelAndIgnoreRemainingEvents()
        }*/
    }

    @Test
    fun `should return error if not successful`() = runTest(testDispatcher.scheduler) {
        // Given

        // Then
        viewModel.homeState.test {
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