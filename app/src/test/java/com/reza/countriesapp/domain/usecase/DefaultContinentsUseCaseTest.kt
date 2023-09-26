package com.reza.countriesapp.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.reza.countriesapp.data.repository.DefaultContinentRepository
import com.reza.countriesapp.domain.model.Continent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class DefaultContinentsUseCaseTest {

    @Mock
    private lateinit var repository: DefaultContinentRepository

    private lateinit var useCase: DefaultContinentsUseCase

    @Before
    fun setup() {
        useCase = DefaultContinentsUseCase(continentRepository = repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return empty list if there is no continent`() = runTest {
        // Given
        whenever(repository.getContinents()).thenReturn(emptyList())

        // When
        val continents = useCase.getContinents()

        // Then
        assertThat(continents).isEmpty()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return list of continents`() = runTest {
        // Given
        whenever(repository.getContinents()).thenReturn(listOf(Continent(name = "name1", code = "code1")))

        // When
        val continents = useCase.getContinents()

        // Then
        assertThat(continents.size).isEqualTo(1)
    }
}