package com.reza.countriesapp.data.datasourse.remote.continent

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.google.common.truth.Truth.assertThat
import com.reza.ContinentQuery
import com.reza.ContinentsQuery
import com.reza.test.ContinentQuery_TestBuilder.Data
import com.reza.test.ContinentsQuery_TestBuilder.Data
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

// TODO: should add error scenarios
class DefaultContinentDataSourceTest {

    private lateinit var apolloClient: ApolloClient

    @OptIn(ApolloExperimental::class)
    @Before
    fun setUp() {
        apolloClient = ApolloClient
            .Builder()
            .networkTransport(QueueTestNetworkTransport())
            .build()
    }

    @OptIn(ApolloExperimental::class)
    @Test
    fun `testing getContinents`() = runTest {
        // Given
        val query = ContinentsQuery()
        val data = ContinentsQuery.Data {
            continents = listOf(
                continent {
                    name = "Africa"
                    code = "AF"
                }, continent {
                    name = "Antarctica"
                    code = "AN"
                }
            )
        }
        apolloClient.enqueueTestResponse(query, data)

        // When
        val actual = apolloClient
            .query(query)
            .execute()
            .data

        // Then
        assertThat(actual?.continents?.first()?.name).isEqualTo(data.continents.first().name)
        assertThat(actual?.continents?.first()?.code).isEqualTo(data.continents.first().code)
    }
}