package com.reza.countriesapp.data.datasourse.remote.continent

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.mockserver.MockRequest
import com.apollographql.apollo3.mockserver.MockResponse
import com.apollographql.apollo3.mockserver.MockServer
import com.apollographql.apollo3.mockserver.MockServerHandler
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.benasher44.uuid.nameBasedUuidOf
import com.reza.ContinentQuery
import com.reza.ContinentsQuery
import com.reza.type.buildContinent
import com.reza.type.buildCountry
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

    private lateinit var apolloClient: ApolloClient

    @OptIn(ApolloExperimental::class)
    @Before
    fun setUp() {
        apolloClient = ApolloClient.Builder()
            .networkTransport(QueueTestNetworkTransport())
            .build()
    }

    @OptIn(ApolloExperimental::class)
    @Test
    fun `testing getContinents`() = runTest {
        val testQuery = ContinentsQuery()
        val testData = ContinentsQuery.Data {
            continents =
                listOf(
                buildContinent {
                    name = "Africa"
                    code = "AF"
                },
                buildContinent {
                    name = "Antarctica"
                    code = "AN"
                }
            )
        }
        //apolloClient.enqueueTestResponse(testQuery, testData)

        val actual = apolloClient
            .query(testQuery)
            .execute()
            .data

        assertEquals("Africa", testData.continents.first().name)
        //assertEquals(testData.continents.last().name, actual?.continents?.last()?.name)

//        assertEquals(testData.continents.first().name, actual?.continents?.first()?.name)
//        assertEquals(testData.continents.last().name, actual?.continents?.last()?.name)
    }


    @Test
    fun `testing continent`() = runTest {
        val testQuery = ContinentQuery("AF")
        val testData = ContinentsQuery.Data {
            buildCountry {

            }
        }
    }
}