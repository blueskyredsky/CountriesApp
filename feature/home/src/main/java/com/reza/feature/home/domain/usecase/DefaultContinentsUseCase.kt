package com.reza.feature.home.domain.usecase

import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent
import com.reza.feature.home.domain.repository.ContinentRepository
import javax.inject.Inject

internal class DefaultContinentsUseCase @Inject constructor(
    private val continentRepository: ContinentRepository
) : ContinentsUseCase {
    override suspend fun getContinents(): ResultState<List<Continent>> {
        return continentRepository.getContinents()
    }
}