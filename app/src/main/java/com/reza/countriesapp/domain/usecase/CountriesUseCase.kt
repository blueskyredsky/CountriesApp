package com.reza.countriesapp.domain.usecase

import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.model.Country
import com.reza.countriesapp.domain.model.ResultState

interface CountriesUseCase {
    suspend fun getCountries(code: String): ResultState<List<Country?>?>
}