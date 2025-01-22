package com.reza.details.data.repository

import com.apollographql.apollo.api.ApolloResponse
import com.google.common.truth.Truth.assertThat
import com.reza.ContinentQuery
import com.reza.common.domain.model.ResultState
import com.reza.details.data.datasource.remote.CountriesRemoteDataSource
import com.reza.details.domain.repository.CountriesRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultCountriesRepositoryTest {

    @Mock
    private lateinit var dataSource: CountriesRemoteDataSource

    @Mock
    private lateinit var apolloResponse: ApolloResponse<ContinentQuery.Data>

    private lateinit var repository: CountriesRepository

    @Before
    fun setup() {
        repository = DefaultCountriesRepository(dataSource)
    }

    @Test
    fun `should return success when calling get countries`() = runTest {
        // Given
        Mockito.`when`(apolloResponse.hasErrors()).thenReturn(false)
        Mockito.`when`(dataSource.getCountries("")).thenReturn(apolloResponse)

        // When
        val continents = repository.getCountries("")

        // Then
        assertThat(continents).isInstanceOf(ResultState.Success::class.java)
    }


    @Test
    fun `should return failure when calling get countries`() = runTest {
        // Given
        Mockito.`when`(apolloResponse.hasErrors()).thenReturn(true)
        Mockito.`when`(dataSource.getCountries("")).thenReturn(apolloResponse)

        // When
        val continents = repository.getCountries("")

        // Then
        assertThat(continents).isInstanceOf(ResultState.Failure::class.java)
    }
}