package com.reza.countriesapp.data.repository

import com.apollographql.apollo3.ApolloClient
import com.reza.ContinentQuery
import com.reza.ContinentsQuery
import com.reza.countriesapp.data.mapper.ContinentMapper
import com.reza.countriesapp.data.mapper.ContinentsMapper
import com.reza.countriesapp.data.mapper.toContinentEntity
import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.repository.ContinentRepository
import javax.inject.Inject

class DefaultContinentRepository @Inject constructor(
    private val apolloClient: ApolloClient,
    private val continentsMapper: ContinentsMapper,
    private val continentMapper: ContinentMapper
) : ContinentRepository {

    override suspend fun getContinents(): List<Continent> {
        return apolloClient
            .query(ContinentsQuery())
            .execute()
            .data
            ?.continents
            ?.map { continentsMapper.mapToDomainModel(it) }
            ?: emptyList()
    }

    override suspend fun getContinentDetails(code: String): Continent? {
        return apolloClient
            .query(ContinentQuery(code))
            .execute()
            .data
            ?.continent
            ?.let {
                continentMapper.mapToDomainModel(model = it)
            }
    }
}