package com.reza.countriesapp.data.repository

import com.apollographql.apollo3.ApolloClient
import com.reza.ContinentsQuery
import com.reza.countriesapp.data.mapper.ContinentsMapper
import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.repository.ContinentRepository
import javax.inject.Inject

class DefaultContinentRepository @Inject constructor(
    private val apolloClient: ApolloClient,
    private val mapper: ContinentsMapper,
) : ContinentRepository {

    override suspend fun getContinents(): List<Continent> {
        return apolloClient
            .query(ContinentsQuery())
            .execute()
            .data
            ?.continents
            ?.map { mapper.mapToDomainModel(it) }
            ?: emptyList()
    }
}