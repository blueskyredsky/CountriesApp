package com.reza.countriesapp.data.repository

import com.google.common.truth.Truth.assertThat
import com.reza.ContinentsQuery
import com.reza.countriesapp.data.datasourse.remote.continent.DefaultContinentDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class DefaultContinentRepositoryTest {

    @Mock
    private lateinit var dataSource: DefaultContinentDataSource

    private lateinit var repository: DefaultContinentRepository

    @Before
    fun setup() {
        repository = DefaultContinentRepository(
            continentDataSource = dataSource
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return empty list when there is no continent`() = runTest {
        // Given
        whenever(dataSource.getContinents()).thenReturn(emptyList())

        // When
        val continents = repository.getContinents()

        // Then
        assertThat(continents.isEmpty())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return a list of continent when calling continents`() = runTest {
        // Given
        val continentList = listOf(
            ContinentsQuery.Continent(name = "Africa", code = "AF")
        )
        whenever(dataSource.getContinents()).thenReturn(continentList)

        // When
        val continents = repository.getContinents()

        // Then
        assertThat(continents.isNotEmpty()).isEqualTo(true)
        assertThat(continents[0].name).isEqualTo("Africa")
    }
}