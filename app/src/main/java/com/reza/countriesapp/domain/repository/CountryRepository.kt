package com.reza.countriesapp.domain.repository

import com.reza.countriesapp.domain.model.Country

interface CountryRepository {
    suspend fun getCountries(code: String): List<Country?>?
}