package com.reza.countriesapp.data.datasourse.remote.continent

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.mockserver.MockRequest
import com.apollographql.apollo3.mockserver.MockResponse
import com.apollographql.apollo3.mockserver.MockServer
import com.apollographql.apollo3.mockserver.MockServerHandler
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.reza.ContinentsQuery
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultContinentDataSourceTest {

    private lateinit var continentDataSource: ContinentDataSource

    @OptIn(ApolloExperimental::class)
    @Before
    fun setUp() {

    }

    @OptIn(ApolloExperimental::class)
    @Test
    fun `should getContinents be called`() = runTest {
        val apolloClient = ApolloClient.Builder()
            .networkTransport(QueueTestNetworkTransport())
            .build()

        val testQuery = ContinentsQuery()
        val testData = ContinentsQuery.Data(listOf(ContinentsQuery.Continent(name = "Africa", code = "AF")))
        apolloClient.enqueueTestResponse(testQuery, testData)

        val actual = apolloClient.query(testQuery).execute().data!!
        assertEquals(testData.continents.first().name, actual.continents.first().name)
    }


    @Test
    fun `should getContinent be called`() = runTest {

    }

    @After
    fun tearDown() {
    }
}