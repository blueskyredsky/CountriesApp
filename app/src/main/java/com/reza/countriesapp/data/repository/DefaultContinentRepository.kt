package com.reza.countriesapp.data.repository

import com.reza.countriesapp.data.datasourse.remote.continent.ContinentDataSource
import com.reza.countriesapp.data.mapper.ContinentsMapper
import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.repository.ContinentRepository
import javax.inject.Inject

class DefaultContinentRepository @Inject constructor(
    private val continentDataSource: ContinentDataSource,
    private val mapper: ContinentsMapper,
) : ContinentRepository {

    override suspend fun getContinents(): List<Continent> {
        return continentDataSource
            .getContinents()
            ?.map { mapper.mapToDomainModel(it) }
            ?: emptyList()
    }
}