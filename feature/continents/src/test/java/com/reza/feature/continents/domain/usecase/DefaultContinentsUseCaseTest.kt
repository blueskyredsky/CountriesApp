package com.reza.feature.continents.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.reza.common.domain.model.ResultState
import com.reza.feature.continents.domain.model.Continent
import com.reza.feature.continents.domain.repository.ContinentRepository
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultContinentsUseCaseTest {

    @Mock
    private lateinit var repository: ContinentRepository

    private lateinit var useCase: ContinentsUseCase

    private val standardTestDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        useCase = DefaultContinentsUseCase(continentRepository = repository, ioDispatcher = standardTestDispatcher)
    }

    @Test
    fun `should return success with data when calling get countries`() = runTest(standardTestDispatcher.scheduler) {
        // Given
        val sampleContinents = listOf(Continent(name = "", code = ""))
        Mockito.`when`(repository.getContinents()).thenReturn(ResultState.Success(sampleContinents))

        // When
        val continents = useCase.getContinents()

        // Then
        assertThat(continents).isInstanceOf(ResultState.Success::class.java)
        assertThat(continents).isEqualTo(ResultState.Success(sampleContinents))
    }

    @Test
    fun `should return failure when calling get countries`() = runTest(standardTestDispatcher.scheduler) {
        // Given
        Mockito.`when`(repository.getContinents()).thenReturn(ResultState.Failure(error = ""))

        // When
        val continents = useCase.getContinents()

        // Then
        assertThat(continents).isInstanceOf(ResultState.Failure::class.java)
    }
}