package com.reza.countriesapp.data.repository

import com.apollographql.apollo3.ApolloClient
import com.reza.ContinentQuery
import com.reza.countriesapp.data.mapper.CountryMapper
import com.reza.countriesapp.domain.model.Country
import com.reza.countriesapp.domain.repository.CountryRepository
import javax.inject.Inject

class DefaultCountryRepository @Inject constructor(
    private val apolloClient: ApolloClient,
    private val mapper: CountryMapper,
) : CountryRepository {
    override suspend fun getCountries(code: String): List<Country?>? {
        return apolloClient
            .query(ContinentQuery(code))
            .execute()
            .data
            ?.continent
            ?.let {
                mapper.mapToDomainModel(model = it)
            }
    }
}