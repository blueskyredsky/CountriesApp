package com.reza.countriesapp.data.repository

import com.google.common.truth.Truth.assertThat
import com.reza.ContinentsQuery
import com.reza.countriesapp.data.datasourse.remote.continent.DefaultContinentDataSource
import com.reza.countriesapp.data.mapper.ContinentsMapper
import com.reza.countriesapp.domain.model.Continent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class DefaultContinentRepositoryTest {

    @Mock
    private lateinit var dataSource: DefaultContinentDataSource

    @Mock
    private lateinit var mapper: ContinentsMapper

    private lateinit var repository: DefaultContinentRepository

    @Before
    fun setup() {
        repository = DefaultContinentRepository(
            continentDataSource = dataSource,
            mapper = mapper
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return empty list if continents is empty`() = runTest {
        // Given
        whenever(dataSource.getContinents()).thenReturn(emptyList())

        // When
        val continents = repository.getContinents()

        // Then
        assertThat(continents.isEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return`() = runTest {
        // Given
        val continentList = listOf(
            ContinentsQuery.Continent(name = "Africa", code = "AF")
        )
        whenever(dataSource.getContinents()).thenReturn(continentList)
        whenever(mapper.mapToDomainModel(any<ContinentsQuery.Continent>())).thenReturn(
            Continent(
                name = "AfricaDomain",
                code = "AFDomain"
            )
        )

        // When
        val continents = repository.getContinents()

        // Then
    }
}