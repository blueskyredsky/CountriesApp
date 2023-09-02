package com.reza.countriesapp.data.datasourse.remote.continent

import com.apollographql.apollo3.ApolloClient
import com.reza.ContinentQuery
import com.reza.ContinentsQuery
import javax.inject.Inject

class DefaultContinentDataSource @Inject constructor(
    private val apolloClient: ApolloClient
) : ContinentDataSource {
    override suspend fun getContinents(): List<ContinentsQuery.Continent>? {
        return apolloClient
            .query(ContinentsQuery())
            .execute()
            .data
            ?.continents
    }

    override suspend fun getContinent(code: String): ContinentQuery.Continent? {
        return apolloClient
            .query(ContinentQuery(continent_code = code))
            .execute()
            .data
            ?.continent
    }
}