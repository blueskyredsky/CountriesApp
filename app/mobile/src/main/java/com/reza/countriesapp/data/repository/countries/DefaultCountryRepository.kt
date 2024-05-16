package com.reza.countriesapp.data.repository.countries

import com.apollographql.apollo3.exception.ApolloException
import com.reza.countriesapp.data.datasourse.remote.countries.CountriesDataSource
import com.reza.countriesapp.data.model.toCountry
import com.reza.countriesapp.domain.model.Country
import com.reza.countriesapp.domain.model.ResultState
import com.reza.countriesapp.domain.repository.countries.CountryRepository
import javax.inject.Inject

class DefaultCountryRepository @Inject constructor(
    private val countriesDataSource: CountriesDataSource
) : CountryRepository {
    override suspend fun getCountries(code: String): ResultState<List<Country?>?> {
        try {
            val response = countriesDataSource.getCountries(code = code)
            return if (!response.hasErrors()) {
                ResultState.Success(
                    response.data?.continent?.toCountry()
                )
            } else {
                ResultState.Failure(
                    response.errors?.getOrNull(0)?.message ?: "Something went wrong"
                )
            }
        } catch (exception: ApolloException) {
            throw exception
        }
    }
}