package com.reza.countriesapp.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.reza.countriesapp.data.repository.DefaultContinentRepository
import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.model.ResultState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class DefaultContinentsUseCaseTest {

    @Mock
    private lateinit var repository: DefaultContinentRepository

    private lateinit var useCase: DefaultContinentsUseCase

    @Before
    fun setup() {
        useCase = DefaultContinentsUseCase(continentRepository = repository)
    }

    @Test
    fun `should return success with data when calling get countries`() = runTest {
        // Given
        val sampleContinents = listOf(Continent(name = "", code = ""))
        whenever(repository.getContinents()).thenReturn(ResultState.Success(sampleContinents))

        // When
        val continents = useCase.getContinents()

        // Then
        assertThat(continents).isInstanceOf(ResultState.Success::class.java)
        assertThat(continents).isEqualTo(ResultState.Success(sampleContinents))
    }

    @Test
    fun `should return failure when calling get countries`() = runTest {
        // Given
        whenever(repository.getContinents()).thenReturn(ResultState.Failure(error = ""))

        // When
        val continents = useCase.getContinents()

        // Then
        assertThat(continents).isInstanceOf(ResultState.Failure::class.java)
    }
}