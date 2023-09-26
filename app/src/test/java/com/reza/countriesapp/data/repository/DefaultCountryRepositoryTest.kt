package com.reza.countriesapp.data.repository

import com.google.common.truth.Truth.assertThat
import com.reza.ContinentQuery
import com.reza.countriesapp.data.datasourse.remote.continent.ContinentDataSource
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
class DefaultCountryRepositoryTest {

    @Mock
    private lateinit var dataSource: ContinentDataSource

    private lateinit var repository: DefaultCountryRepository

    @Before
    fun setup() {
        repository = DefaultCountryRepository(
            continentDataSource = dataSource
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return null when there is no continent`() = runTest {
        // Given
        whenever(dataSource.getContinent(code = anyString())).thenReturn(null)

        // When
        val continents = repository.getCountries(code = "AA")

        // Then
        assertThat(continents).isNull()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return list of countries when there is a continent`() = runTest {
        // Given
        whenever(dataSource.getContinent(code = anyString())).thenReturn(
            ContinentQuery.Continent(
                countries = listOf(
                    ContinentQuery.Country(
                        name = "name1",
                        emoji = "",
                        currency = "currency1",
                        capital = "capital1",
                        phone = "phone1",
                        languages = listOf(ContinentQuery.Language("language1")),
                        states = listOf(ContinentQuery.State("state1"))
                    )
                )
            )
        )

        // When
        val countries = repository.getCountries("TEST")

        // Then
        assertThat(countries?.size).isEqualTo(1)
    }
}