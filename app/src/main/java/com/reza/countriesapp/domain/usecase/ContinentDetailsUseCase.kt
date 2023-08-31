package com.reza.countriesapp.domain.usecase

import com.reza.countriesapp.domain.model.Continent

interface ContinentDetailsUseCase {
    suspend fun getContinentDetails(code: String): Continent?
}