package com.reza.feature.continents.domain.usecase

import com.reza.common.domain.model.ResultState
import com.reza.feature.continents.domain.model.Continent
import com.reza.feature.continents.domain.repository.ContinentRepository
import javax.inject.Inject

internal class DefaultContinentsUseCase @Inject constructor(
    private val continentRepository: ContinentRepository
) : ContinentsUseCase {
    override suspend fun getContinents(): ResultState<List<Continent>> {
        return continentRepository.getContinents()
    }
}