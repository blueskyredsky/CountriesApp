package com.reza.feature.continents.domain.usecase

import com.reza.common.domain.model.ResultState
import com.reza.feature.continents.domain.model.Continent

/**
 * A use case interface for retrieving continent data.
 */
interface ContinentsUseCase {
    /**
     * Retrieves a list of continents.
     */
    suspend fun getContinents(): ResultState<List<Continent>>
}