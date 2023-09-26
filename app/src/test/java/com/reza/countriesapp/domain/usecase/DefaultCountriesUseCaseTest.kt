package com.reza.countriesapp.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.reza.countriesapp.data.repository.DefaultCountryRepository
import com.reza.countriesapp.domain.model.Country
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return empty list if there is no continent`() = runTest {
        // Given
        whenever(repository.getCountries(anyString())).thenReturn(emptyList())

        // When
        val countries = useCase.getCountries("test")

        // Then
        assertThat(countries).isEmpty()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return list of countries`() = runTest {
        // Given
        whenever(repository.getCountries(anyString())).thenReturn(
            listOf(
                Country(
                    name = "name1",
                    emoji = "",
                    currency = "currency1",
                    capital = "capital1",
                    phone = "phone1",
                    states = listOf("state1"),
                    languages = listOf("language1")
                )
            )
        )

        // When
        val counties = useCase.getCountries("test")

        // Then
        assertThat(counties?.size).isEqualTo(1)
    }
}