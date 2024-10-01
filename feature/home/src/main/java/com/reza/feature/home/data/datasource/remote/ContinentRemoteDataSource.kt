package com.reza.feature.home.data.datasource.remote

import com.apollographql.apollo.api.ApolloResponse
import com.reza.ContinentsQuery

internal interface ContinentRemoteDataSource {
    suspend fun getContinents(): ApolloResponse<ContinentsQuery.Data>
}