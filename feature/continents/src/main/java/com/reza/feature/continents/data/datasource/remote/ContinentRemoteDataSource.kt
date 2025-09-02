package com.reza.feature.continents.data.datasource.remote

import com.apollographql.apollo.api.ApolloResponse
import com.reza.ContinentsQuery

/**
 * An internal remote data source for retrieving continent data.
 */
internal interface ContinentRemoteDataSource {
    /**
     * Retrieves a list of continents from a remote source.
     */
    suspend fun getContinents(): ApolloResponse<ContinentsQuery.Data>
}