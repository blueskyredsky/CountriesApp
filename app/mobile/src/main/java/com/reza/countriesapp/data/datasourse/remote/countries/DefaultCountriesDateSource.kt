package com.reza.countriesapp.data.datasourse.remote.countries

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.reza.ContinentQuery
import javax.inject.Inject

class DefaultCountriesDateSource @Inject constructor(
    private val apolloClient: ApolloClient
) : CountriesDataSource {
    override suspend fun getCountries(code: String): ApolloResponse<ContinentQuery.Data> {
        return apolloClient
            .query(ContinentQuery(continent_code = code))
            .execute()
    }
}