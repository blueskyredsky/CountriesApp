package com.reza.countriesapp.domain.repository.continent

import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.model.ResultState

interface ContinentRepository {
    suspend fun getContinents(): ResultState<List<Continent>>
}