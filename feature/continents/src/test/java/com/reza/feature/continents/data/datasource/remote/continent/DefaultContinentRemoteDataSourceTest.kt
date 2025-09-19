package com.reza.feature.continents.data.datasource.remote.continent

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.annotations.ApolloExperimental
import com.apollographql.apollo.testing.QueueTestNetworkTransport
import com.apollographql.apollo.testing.enqueueTestResponse
import com.google.common.truth.Truth.assertThat
import com.reza.ContinentsQuery
import com.reza.feature.continents.data.datasource.remote.ContinentRemoteDataSource
import com.reza.feature.continents.data.datasource.remote.DefaultContinentRemoteDataSource
import com.reza.feature.continents.domain.model.Continent
import com.reza.type.buildContinent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DefaultContinentRemoteDataSourceTest {

    private lateinit var continentRemoteDataSource: ContinentRemoteDataSource

    private lateinit var apolloClient: ApolloClient

    @OptIn(ApolloExperimental::class)
    @Before
    fun setUp() {
        apolloClient = ApolloClient.Builder()
            .networkTransport(QueueTestNetworkTransport())
            .build()

        continentRemoteDataSource = DefaultContinentRemoteDataSource(apolloClient)
    }

    @OptIn(ApolloExperimental::class)
    @Test
    fun `testing getContinents`() = runTest {
        // Given
        val testQuery = ContinentsQuery()
        val data = ContinentsQuery.Data {
            continents = listOf(
                buildContinent {
                    name = Continent.AFRICA
                    code = Continent.AFRICA_CODE
                },
                buildContinent {
                    name = Continent.ANTARCTICA
                    code = Continent.ANTARCTICA_CODE
                }
            )
        }
        apolloClient.enqueueTestResponse(testQuery, data)

        // When
        val result = continentRemoteDataSource.getContinents().data

        // Then
        assertThat(result?.continents?.first()?.name).isEqualTo(data.continents.first().name)
        assertThat(result?.continents?.first()?.code).isEqualTo(data.continents.first().code)
        assertThat(result?.continents?.last()?.code).isEqualTo(data.continents.last().code)
        assertThat(result?.continents?.last()?.code).isEqualTo(data.continents.last().code)
    }
}