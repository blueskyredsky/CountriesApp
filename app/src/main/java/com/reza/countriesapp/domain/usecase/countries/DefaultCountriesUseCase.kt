package com.reza.countriesapp.domain.usecase.countries

import com.reza.countriesapp.domain.model.Country
import com.reza.countriesapp.domain.model.ResultState
import com.reza.countriesapp.domain.repository.countries.CountryRepository
import javax.inject.Inject

class DefaultCountriesUseCase @Inject constructor(
    private val countryRepository: CountryRepository
) : CountriesUseCase {
    override suspend fun getCountries(code: String): ResultState<List<Country?>?> {
        return countryRepository.getCountries(code)
    }
}