package com.reza.details.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.reza.common.domain.model.ResultState
import com.reza.details.domain.model.Country
import com.reza.details.domain.repository.CountriesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultCountriesUseCaseTest {
    @Mock
    private lateinit var repository: CountriesRepository

    private lateinit var useCase: CountriesUseCase

    @Before
    fun setup() {
        useCase = DefaultCountriesUseCase(countriesRepository = repository)
    }

    @Test
    fun `should return success with data when calling get countries`() = runTest {
        // Given
        val sampleCountries = listOf(
            Country(
                name = "",
                emoji = "",
                currency = "",
                capital = "",
                phone = "",
                states = emptyList(),
                languages = emptyList()
            )
        )
        Mockito.`when`(repository.getCountries("")).thenReturn(ResultState.Success(sampleCountries))

        // When
        val continents = useCase.getCountries("")

        // Then
        assertThat(continents).isInstanceOf(ResultState.Success::class.java)
        assertThat(continents).isEqualTo(ResultState.Success(sampleCountries))
    }

    @Test
    fun `should return failure when calling get countries`() = runTest {
        // Given
        Mockito.`when`(repository.getCountries("")).thenReturn(ResultState.Failure(error = ""))

        // When
        val continents = useCase.getCountries("")

        // Then
        assertThat(continents).isInstanceOf(ResultState.Failure::class.java)
    }
}