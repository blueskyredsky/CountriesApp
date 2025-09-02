package com.reza.feature.continents.data.datasource.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.reza.ContinentsQuery
import javax.inject.Inject

internal class DefaultContinentRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) : ContinentRemoteDataSource {

    override suspend fun getContinents(): ApolloResponse<ContinentsQuery.Data> {
        return apolloClient
            .query(ContinentsQuery())
            .execute()
    }
}