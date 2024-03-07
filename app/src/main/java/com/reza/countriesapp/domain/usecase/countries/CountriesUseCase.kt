package com.reza.countriesapp.domain.usecase.countries

import com.reza.countriesapp.domain.model.Country
import com.reza.countriesapp.domain.model.ResultState

interface CountriesUseCase {
    suspend fun getCountries(code: String): ResultState<List<Country?>?>
}