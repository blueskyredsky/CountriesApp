package com.reza.countriesapp.domain.usecase.continents

import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.model.ResultState
import com.reza.countriesapp.domain.repository.continent.ContinentRepository
import javax.inject.Inject

class DefaultContinentsUseCase @Inject constructor(
    private val continentRepository: ContinentRepository
) : ContinentsUseCase {
    override suspend fun getContinents(): ResultState<List<Continent>> {
        return continentRepository.getContinents()
    }
}