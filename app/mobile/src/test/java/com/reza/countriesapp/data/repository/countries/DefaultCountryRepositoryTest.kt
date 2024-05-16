package com.reza.countriesapp.data.repository.countries

import com.apollographql.apollo3.api.ApolloResponse
import com.google.common.truth.Truth.assertThat
import com.reza.ContinentQuery
import com.reza.countriesapp.data.datasourse.remote.countries.CountriesDataSource
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
class DefaultCountryRepositoryTest {

    @Mock
    private lateinit var dataSource: CountriesDataSource

    @Mock
    private lateinit var apolloResponse: ApolloResponse<ContinentQuery.Data>

    private lateinit var repository: DefaultCountryRepository

    @Before
    fun setup() {
        repository = DefaultCountryRepository(
            countriesDataSource = dataSource
        )
    }

    @Test
    fun `should return success when calling get countries`() = runTest {
        // Given
        whenever(dataSource.getCountries(code = anyString())).thenReturn(apolloResponse)
        whenever(apolloResponse.hasErrors()).thenReturn(false)

        // When
        val continents = repository.getCountries(code = "AA")

        // Then
        assertThat(continents).isInstanceOf(ResultState.Success::class.java)
    }

    @Test
    fun `should return failure when calling get countries`() = runTest {
        // Given
        whenever(dataSource.getCountries(code = anyString())).thenReturn(apolloResponse)
        whenever(apolloResponse.hasErrors()).thenReturn(true)

        // When
        val continents = repository.getCountries(code = "AA")

        // Then
        assertThat(continents).isInstanceOf(ResultState.Failure::class.java)
    }
}