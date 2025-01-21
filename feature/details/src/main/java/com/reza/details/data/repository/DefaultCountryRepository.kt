package com.reza.details.data.repository

import com.apollographql.apollo.exception.ApolloException
import com.reza.common.domain.model.ResultState
import com.reza.details.data.datasource.remote.CountriesRemoteDataSource
import com.reza.details.data.mapper.toCountry
import com.reza.details.domain.model.Country
import com.reza.details.domain.repository.CountriesRepository
import javax.inject.Inject

internal class DefaultCountryRepository @Inject constructor(
    private val countriesDataSource: CountriesRemoteDataSource
) : CountriesRepository {
    override suspend fun getCountries(code: String): ResultState<List<Country>> {
        try {
            val response = countriesDataSource.getCountries(code = code)
            return if (!response.hasErrors()) {
                ResultState.Success(
                    response.data?.continent?.toCountry() ?: emptyList()
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