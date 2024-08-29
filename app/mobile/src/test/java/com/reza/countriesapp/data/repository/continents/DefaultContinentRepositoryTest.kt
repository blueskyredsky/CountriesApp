//package com.reza.countriesapp.data.repository.continents
//
//import com.apollographql.apollo3.api.ApolloResponse
//import com.google.common.truth.Truth.assertThat
//import com.reza.ContinentsQuery
//import com.reza.countriesapp.data.datasourse.remote.continent.DefaultContinentDataSource
//import com.reza.countriesapp.domain.model.ResultState
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.Mock
//import org.mockito.junit.MockitoJUnitRunner
//import org.mockito.kotlin.whenever
//
//@RunWith(MockitoJUnitRunner::class)
//class DefaultContinentRepositoryTest {
//
//    @Mock
//    private lateinit var dataSource: DefaultContinentDataSource
//
//    @Mock
//    private lateinit var apolloResponse:  ApolloResponse<ContinentsQuery.Data>
//
//    private lateinit var repository: DefaultContinentRepository
//
//    @Before
//    fun setup() {
//        repository = DefaultContinentRepository(
//            continentDataSource = dataSource
//        )
//    }
//
//    @Test
//    fun `should return success when calling get continents`() = runTest {
//        // Given
//        whenever(apolloResponse.hasErrors()).thenReturn(false)
//        whenever(dataSource.getContinents()).thenReturn(apolloResponse)
//
//        // When
//        val continents = repository.getContinents()
//
//        // Then
//        assertThat(continents).isInstanceOf(ResultState.Success::class.java)
//    }
//
//
//    @Test
//    fun `should return failure when calling get continents`() = runTest {
//        // Given
//        whenever(apolloResponse.hasErrors()).thenReturn(true)
//        whenever(dataSource.getContinents()).thenReturn(apolloResponse)
//
//        // When
//        val continents = repository.getContinents()
//
//        // Then
//        assertThat(continents).isInstanceOf(ResultState.Failure::class.java)
//    }
//}