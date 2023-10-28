package com.reza.countriesapp.data.repository

import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.reza.countriesapp.data.datasourse.remote.continent.ContinentDataSource
import com.reza.countriesapp.data.model.toContinent
import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.model.ResultState
import com.reza.countriesapp.domain.repository.ContinentRepository
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class DefaultContinentRepository @Inject constructor(
    private val continentDataSource: ContinentDataSource
) : ContinentRepository {

    override suspend fun getContinents(): ResultState<List<Continent>> {
        try {
            val response = continentDataSource.getContinents()
            return if (response.errors == null) {
                val continents = response.data?.continents
                    ?.map {
                        it.toContinent()
                    }?.sortedBy { it.name } ?: emptyList()
                ResultState.Success(continents)
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