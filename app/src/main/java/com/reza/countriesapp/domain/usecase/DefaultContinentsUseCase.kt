package com.reza.countriesapp.domain.usecase

import com.reza.countriesapp.domain.entity.ContinentEntity
import com.reza.countriesapp.domain.repository.ContinentRepository
import javax.inject.Inject

class DefaultContinentsUseCase @Inject constructor(
    private val continentRepository: ContinentRepository
) : ContinentsUseCase {
    override suspend fun getContinents(): List<ContinentEntity> {
        return continentRepository.getContinents().sortedBy { it.name }
    }
}