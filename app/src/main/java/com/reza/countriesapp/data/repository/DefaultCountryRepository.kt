package com.reza.countriesapp.data.repository

import com.apollographql.apollo3.exception.ApolloException
import com.reza.countriesapp.data.datasourse.remote.continent.ContinentDataSource
import com.reza.countriesapp.data.model.toCountry
import com.reza.countriesapp.domain.model.Country
import com.reza.countriesapp.domain.model.ResultState
import com.reza.countriesapp.domain.repository.CountryRepository
import javax.inject.Inject

class DefaultCountryRepository @Inject constructor(
    private val continentDataSource: ContinentDataSource
) : CountryRepository {
    override suspend fun getCountries(code: String): ResultState<List<Country?>?> {
        try {
            val response = continentDataSource.getContinent(code = code)
            return if (response.errors == null) {
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