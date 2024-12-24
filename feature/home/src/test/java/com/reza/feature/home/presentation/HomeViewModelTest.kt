package com.reza.feature.home.presentation


import app.cash.turbine.test
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent
import com.reza.feature.home.domain.usecase.ContinentImageUseCase
import com.reza.feature.home.domain.usecase.ContinentsUseCase
import com.reza.unit.util.MainDispatcherRule
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.stub

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

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
            continentsImageUseCase = continentImageUseCase
        )
    }

    @Test
    fun `testing default values of ui state`() {
        Truth.assertThat(viewModel.homeState.value.continents).isEqualTo(emptyList<Continent>())
        Truth.assertThat(viewModel.homeState.value.errorMessage).isNull()
        Truth.assertThat(viewModel.homeState.value.isLoading).isFalse()
    }

    @Test
    fun `should return list of continents if successful`() = runTest(testDispatcher.scheduler) {
        // Given

//        coEvery { continentsUseCase.getContinents() } returns ResultState.Success(Continent.LIST_OF_CONTINENTS)
        //every { continentImageUseCase.findContinentImage(any()) } returns 1

        whenever(continentImageUseCase.findContinentImage(any())).thenReturn(1)
        continentsUseCase.stub {
            onBlocking { getContinents() } doReturn ResultState.Success(Continent.LIST_OF_CONTINENTS)
        }

        // Then
        viewModel.homeState.test {
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
            cancelAndIgnoreRemainingEvents()
        }
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