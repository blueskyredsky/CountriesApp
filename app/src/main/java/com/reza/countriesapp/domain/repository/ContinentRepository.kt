package com.reza.countriesapp.domain.repository

import com.reza.countriesapp.domain.model.Continent

interface ContinentRepository {
    suspend fun getContinents(): List<Continent>
    suspend fun getContinentDetails(code: String): Continent?
}