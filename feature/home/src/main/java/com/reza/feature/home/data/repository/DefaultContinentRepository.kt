package com.reza.feature.home.data.repository

import com.apollographql.apollo.exception.ApolloException
import com.reza.common.domain.model.ResultState
import com.reza.feature.home.data.datasource.remote.ContinentRemoteDataSource
import com.reza.feature.home.data.mapper.toContinent
import com.reza.feature.home.domain.model.Continent
import com.reza.feature.home.domain.repository.ContinentRepository
import javax.inject.Inject

internal class DefaultContinentRepository @Inject constructor(
    private val continentRemoteDataSource: ContinentRemoteDataSource
) : ContinentRepository {

    override suspend fun getContinents(): ResultState<List<Continent>> {
        try {
            val response = continentRemoteDataSource.getContinents()
            return if (!response.hasErrors()) {
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