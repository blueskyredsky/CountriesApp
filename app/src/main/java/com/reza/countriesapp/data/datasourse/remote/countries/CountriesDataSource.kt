package com.reza.countriesapp.data.datasourse.remote.countries

import com.apollographql.apollo.api.ApolloResponse
import com.reza.ContinentQuery

interface CountriesDataSource {
    suspend fun getCountries(code: String): ApolloResponse<ContinentQuery.Data>
}