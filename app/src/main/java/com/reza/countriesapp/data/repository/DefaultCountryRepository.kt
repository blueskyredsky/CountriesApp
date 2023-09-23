package com.reza.countriesapp.data.repository

import com.reza.countriesapp.data.datasourse.remote.continent.ContinentDataSource
import com.reza.countriesapp.data.mapper.toCountry
import com.reza.countriesapp.domain.model.Country
import com.reza.countriesapp.domain.repository.CountryRepository
import javax.inject.Inject

class DefaultCountryRepository @Inject constructor(
    private val continentDataSource: ContinentDataSource
) : CountryRepository {
    override suspend fun getCountries(code: String): List<Country?>? {
        return continentDataSource
            .getContinent(code = code)
            ?.toCountry()
    }
}