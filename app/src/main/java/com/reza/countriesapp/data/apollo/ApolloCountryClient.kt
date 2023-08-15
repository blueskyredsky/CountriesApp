package com.reza.countriesapp.data.apollo

import com.apollographql.apollo3.ApolloClient
import com.reza.ContinentsQuery
import com.reza.countriesapp.domain.Continent
import com.reza.countriesapp.domain.Country
import com.reza.countriesapp.domain.CountryClient

class ApolloCountryClient(
    private val apolloClient: ApolloClient
) : CountryClient {
    override suspend fun getContinents(): List<Continent> {
        return apolloClient
            .query(ContinentsQuery())
            .execute()
            .data
            ?.continents
            ?.map {
                Continent(name = it.name, code = it.code)
            } ?: emptyList()
    }

    override suspend fun getCountries(code: String): List<Country>? {
        TODO("Not yet implemented")
    }
}