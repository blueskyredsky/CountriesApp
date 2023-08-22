package com.reza.countriesapp.domain.repository

import com.reza.countriesapp.domain.entity.ContinentEntity
import com.reza.countriesapp.domain.entity.CountryEntity

interface ContinentRepository {
    suspend fun getContinents(): List<ContinentEntity>
    suspend fun getContinent(code: String): ContinentEntity?
}