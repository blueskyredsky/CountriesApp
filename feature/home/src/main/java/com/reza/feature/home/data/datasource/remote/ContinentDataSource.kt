package com.reza.feature.home.data.datasource.remote

import com.apollographql.apollo3.api.ApolloResponse
import com.reza.ContinentsQuery

internal interface ContinentDataSource {
    suspend fun getContinents(): ApolloResponse<ContinentsQuery.Data>
}