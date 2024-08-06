package com.reza.feature.home.data.datasource.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.reza.ContinentsQuery
import javax.inject.Inject

internal class DefaultContinentDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) : ContinentDataSource {
    override suspend fun getContinents(): ApolloResponse<ContinentsQuery.Data> {
        return apolloClient
            .query(ContinentsQuery())
            .execute()
    }
}