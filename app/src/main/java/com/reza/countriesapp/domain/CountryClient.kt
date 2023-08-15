package com.reza.countriesapp.domain

interface CountryClient {
    suspend fun getContinents(): List<Continent>
    suspend fun getCountries(code: String): List<Country>?
}