package com.reza.countriesapp.domain.usecase

import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.model.Country

interface CountriesUseCase {
    suspend fun getCountries(code: String): List<Country?>?
}