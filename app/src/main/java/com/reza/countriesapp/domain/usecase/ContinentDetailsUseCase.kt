package com.reza.countriesapp.domain.usecase

import com.reza.countriesapp.domain.entity.ContinentEntity

interface ContinentDetailsUseCase {
    suspend fun getContinentDetails(code: String): ContinentEntity?
}