package com.reza.countriesapp.data.repository

import com.reza.countriesapp.data.datasourse.remote.continent.ContinentDataSource
import com.reza.countriesapp.data.mapper.CountryMapper
import com.reza.countriesapp.domain.model.Country
import com.reza.countriesapp.domain.repository.CountryRepository
import javax.inject.Inject

class DefaultCountryRepository @Inject constructor(
    private val continentDataSource: ContinentDataSource,
    private val mapper: CountryMapper,
) : CountryRepository {
    override suspend fun getCountries(code: String): List<Country?>? {
        return continentDataSource
            .getContinent(code = code)
            ?.let {
                mapper.mapToDomainModel(model = it)
            }
    }
}