package com.reza.countries.data.datasource.remote

import com.apollographql.apollo.api.ApolloResponse
import com.reza.ContinentQuery

/**
 * A data source interface for retrieving country data.
 */
internal interface CountriesRemoteDataSource {
    /**
     * Retrieves a list of countries for a given continent code.
     */
    suspend fun getCountries(code: String): ApolloResponse<ContinentQuery.Data>
}