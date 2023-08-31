package com.reza.countriesapp.domain.usecase

import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.repository.ContinentRepository
import javax.inject.Inject

class DefaultContinentDetailsUseCase @Inject constructor(
    private val continentRepository: ContinentRepository
) : ContinentDetailsUseCase {
    override suspend fun getContinentDetails(code: String): Continent? {
        return continentRepository.getContinentDetails(code = code)
    }
}