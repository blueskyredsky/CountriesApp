package com.reza.countriesapp.data.repository

import com.apollographql.apollo3.ApolloClient
import com.reza.ContinentQuery
import com.reza.ContinentsQuery
import com.reza.countriesapp.data.mapper.toContinentEntity
import com.reza.countriesapp.domain.entity.ContinentEntity
import com.reza.countriesapp.domain.repository.ContinentRepository
import javax.inject.Inject

class DefaultContinentRepository @Inject constructor(
    private val apolloClient: ApolloClient
) : ContinentRepository {

    override suspend fun getContinents(): List<ContinentEntity> {
        return apolloClient
            .query(ContinentsQuery())
            .execute()
            .data
            ?.continents
            ?.map { it.toContinentEntity() }
            ?: emptyList()
    }

    override suspend fun getContinentDetails(code: String): ContinentEntity? {
        return apolloClient
            .query(ContinentQuery(code))
            .execute()
            .data
            ?.continent
            ?.toContinentEntity()
    }
}