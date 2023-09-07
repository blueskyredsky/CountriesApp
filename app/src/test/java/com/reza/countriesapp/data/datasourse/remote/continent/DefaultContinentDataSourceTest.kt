package com.reza.countriesapp.data.datasourse.remote.continent

import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DefaultContinentDataSourceTest {

    @Mock
    private lateinit var apolloClient: ApolloClient

    private lateinit var continentDataSource: ContinentDataSource

    @Before
    fun setUp() {
        continentDataSource = DefaultContinentDataSource(apolloClient)
    }

    @Test
    fun `should getContinents be called`() = runTest {

    }


    @Test
    fun `should getContinent be called`() = runTest {

    }

    @After
    fun tearDown() {
    }
}