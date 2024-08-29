package com.reza.feature.home.data.datasource.remote.continent

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.annotations.ApolloExperimental
import com.apollographql.apollo.testing.QueueTestNetworkTransport
import com.apollographql.apollo.testing.enqueueTestResponse
import com.google.common.truth.Truth.assertThat
import com.reza.ContinentsQuery
import com.reza.type.ContinentMap
import com.reza.type.buildCountry
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

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
                ContinentMap(
                    mapOf(("Antarctica" to "AN"), ("Africa" to "AF"))
                )
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