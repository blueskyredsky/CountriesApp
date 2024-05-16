package com.reza.countriesapp.domain.usecase.countries

import com.google.common.truth.Truth.assertThat
import com.reza.countriesapp.data.repository.countries.DefaultCountryRepository
import com.reza.countriesapp.domain.model.Country
import com.reza.countriesapp.domain.model.ResultState
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class DefaultCountriesUseCaseTest {

    @Mock
    private lateinit var repository: DefaultCountryRepository

    private lateinit var useCase: DefaultCountriesUseCase

    @Before
    fun setup() {
        useCase = DefaultCountriesUseCase(countryRepository = repository)
    }

    @Test
    fun `should return success with null when calling get countries`() = runTest {
        // Given
        whenever(repository.getCountries(anyString())).thenReturn(ResultState.Success(null))

        // When
        val countries = useCase.getCountries("test")

        // Then
        assertThat(countries).isInstanceOf(ResultState.Success::class.java)
        assertThat(countries).isEqualTo(ResultState.Success(null))
    }

    @Test
    fun `should return success with some data when calling get countries`() = runTest {
        // Given
        val sampleCountries = listOf(
            Country(
                name = "testName",
                emoji = "",
                currency = "",
                capital = "",
                phone = "",
                states = listOf(""),
                languages = listOf("")
            )
        )
        whenever(repository.getCountries(anyString())).thenReturn(ResultState.Success(sampleCountries))

        // When
        val countries = useCase.getCountries("test")

        // Then
        assertThat(countries).isInstanceOf(ResultState.Success::class.java)
        assertThat(countries).isEqualTo(ResultState.Success(sampleCountries))
    }

    @Test
    fun `should return failure when calling get countries`() = runTest {
        // Given
        whenever(repository.getCountries(anyString())).thenReturn(ResultState.Failure(error = ""))

        // When
        val countries = useCase.getCountries("test")

        // Then
        assertThat(countries).isInstanceOf(ResultState.Failure::class.java)
    }
}