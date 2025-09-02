package com.reza.feature.continents.data.repository

import com.apollographql.apollo.api.ApolloResponse
import com.google.common.truth.Truth.assertThat
import com.reza.ContinentsQuery
import com.reza.common.domain.model.ResultState
import com.reza.feature.continents.data.datasource.remote.ContinentRemoteDataSource
import com.reza.feature.continents.domain.repository.ContinentRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultContinentRepositoryTest {

    @Mock
    private lateinit var dataSource: ContinentRemoteDataSource

    @Mock
    private lateinit var apolloResponse: ApolloResponse<ContinentsQuery.Data>

    private lateinit var repository: ContinentRepository

    @Before
    fun setup() {
        repository = DefaultContinentRepository(dataSource)
    }

    @Test
    fun `should return success when calling get continents`() = runTest {
        // Given
        Mockito.`when`(apolloResponse.hasErrors()).thenReturn(false)
        Mockito.`when`(dataSource.getContinents()).thenReturn(apolloResponse)

        // When
        val continents = repository.getContinents()

        // Then
        assertThat(continents).isInstanceOf(ResultState.Success::class.java)
    }


    @Test
    fun `should return failure when calling get continents`() = runTest {
        // Given
        Mockito.`when`(apolloResponse.hasErrors()).thenReturn(true)
        Mockito.`when`(dataSource.getContinents()).thenReturn(apolloResponse)

        // When
        val continents = repository.getContinents()

        // Then
        assertThat(continents).isInstanceOf(ResultState.Failure::class.java)
    }
}