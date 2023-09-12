package com.reza.countriesapp.data.datasourse.remote.continent

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.mockserver.MockRequest
import com.apollographql.apollo3.mockserver.MockResponse
import com.apollographql.apollo3.mockserver.MockServer
import com.apollographql.apollo3.mockserver.MockServerHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
//@RunWith(MockitoJUnitRunner::class)
class DefaultContinentDataSourceTest {

    @Mock
    private lateinit var apolloClient: ApolloClient

    private lateinit var continentDataSource: ContinentDataSource

    @Before
    fun setUp() {

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