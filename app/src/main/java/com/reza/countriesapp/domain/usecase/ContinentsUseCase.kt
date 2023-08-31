package com.reza.countriesapp.domain.usecase

import com.reza.countriesapp.domain.model.Continent

interface ContinentsUseCase {
    suspend fun getContinents(): List<Continent>
}