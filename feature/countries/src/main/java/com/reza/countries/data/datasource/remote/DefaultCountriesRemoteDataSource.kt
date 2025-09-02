package com.reza.countries.data.datasource.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.reza.ContinentQuery
import javax.inject.Inject

internal class DefaultCountriesRemoteDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) : CountriesRemoteDataSource {

    override suspend fun getCountries(code: String): ApolloResponse<ContinentQuery.Data> {
        return apolloClient
            .query(ContinentQuery(continent_code = code))
            .execute()
    }
}