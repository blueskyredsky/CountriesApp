package com.reza.countriesapp.domain.usecase

import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.model.ResultState

interface ContinentsUseCase {
    suspend fun getContinents(): ResultState<List<Continent>>
}