package com.reza.countriesapp.data.datasourse.remote.countries

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.testing.QueueTestNetworkTransport
import com.apollographql.apollo3.testing.enqueueTestResponse
import com.google.common.truth.Truth
import com.reza.ContinentQuery

import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

// TODO: should add error scenarios
class DefaultCountriesDateSourceTest {

    private lateinit var apolloClient: ApolloClient

    @OptIn(ApolloExperimental::class)
    @Before
    fun setUp() {
        apolloClient = ApolloClient
            .Builder()
            .networkTransport(QueueTestNetworkTransport())
            .build()
    }

//    @OptIn(ApolloExperimental::class)
//    @Test
//    fun `testing getContinent`() = runTest {
//        // Given
//        val query = ContinentQuery("AF")
//        val data = ContinentQuery.Data {
//            continent = continent {
//                countries = listOf(country {
//                    name = "Angola"
//                    emoji = "\uD83C\uDDE6"
//                    currency = "AOA"
//                    capital = "Luanda"
//                    phone = "244"
//                    languages = listOf(language {
//                        name = "Portuguese"
//                    })
//                    states = listOf()
//                })
//            }
//        }
//        apolloClient.enqueueTestResponse(query, data)
//
//        // When
//        val actual = apolloClient
//            .query(query)
//            .execute()
//            .data
//
//        // Then
//        Truth.assertThat(data.continent?.countries?.first()?.currency).isEqualTo(actual?.continent?.countries?.first()?.currency)
//        Truth.assertThat(data.continent?.countries?.first()?.name).isEqualTo(actual?.continent?.countries?.first()?.name)
//    }
}