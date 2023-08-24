package com.reza.countriesapp.domain.usecase

import com.reza.countriesapp.domain.entity.ContinentEntity

interface ContinentsUseCase {
    suspend fun getContinents(): List<ContinentEntity>
}