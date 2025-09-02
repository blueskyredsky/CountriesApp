package com.reza.countries.data.datasource.remote.countries

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.annotations.ApolloExperimental
import com.apollographql.apollo.testing.QueueTestNetworkTransport
import com.apollographql.apollo.testing.enqueueTestResponse
import com.google.common.truth.Truth.assertThat
import com.reza.ContinentQuery
import com.reza.type.buildContinent
import com.reza.type.buildCountry
import com.reza.type.buildLanguage
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DefaultCountriesDateSourceTest {

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
    fun `testing getContinent`() = runTest {
        // Given
        val query = ContinentQuery("AF")
        val data = ContinentQuery.Data {
            continent = buildContinent {
                countries = listOf(
                    buildCountry {
                        name = "Angola"
                        emoji = "\uD83C\uDDE6"
                        currency = "AOA"
                        capital = "Luanda"
                        phone = "244"
                        languages = listOf(buildLanguage {
                            name = "Portuguese"
                        })
                        states = listOf()
                    })
            }
        }
        apolloClient.enqueueTestResponse(query, data)

        // When
        val actual = apolloClient
            .query(query)
            .execute()
            .data

        // Then
        assertThat(data.continent?.countries?.first()?.currency)
            .isEqualTo(actual?.continent?.countries?.first()?.currency)
        assertThat(data.continent?.countries?.first()?.name)
            .isEqualTo(actual?.continent?.countries?.first()?.name)
    }
}