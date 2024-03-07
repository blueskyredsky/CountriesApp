package com.reza.countriesapp.domain.usecase.continents

import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.model.ResultState

interface ContinentsUseCase {
    suspend fun getContinents(): ResultState<List<Continent>>
}