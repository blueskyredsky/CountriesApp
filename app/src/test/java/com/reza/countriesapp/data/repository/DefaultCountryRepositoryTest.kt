package com.reza.countriesapp.data.repository

import com.google.common.truth.Truth.assertThat
import com.reza.countriesapp.data.datasourse.remote.continent.ContinentDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
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
    fun `should return empty list`() = runTest {
        // Given
        whenever(dataSource.getContinent("AA")).thenReturn(null)

        // When
        val continents = repository.getCountries("AA")

        // Then
        assertThat(continents).isNull()
    }
}