package com.reza.feature.home.domain.usecase

import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent

/**
 * A use case interface for retrieving continent data.
 */
interface ContinentsUseCase {
    /**
     * Retrieves a list of continents.
     */
    suspend fun getContinents(): ResultState<List<Continent>>
}