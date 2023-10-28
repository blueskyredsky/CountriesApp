package com.reza.countriesapp.domain.repository

import com.reza.countriesapp.domain.model.Country
import com.reza.countriesapp.domain.model.ResultState

interface CountryRepository {
    suspend fun getCountries(code: String): ResultState<List<Country?>?>
}