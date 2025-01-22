package com.reza.feature.home.domain.usecase

import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent

/**
 * An internal use case interface for retrieving continent data.
 */
internal interface ContinentsUseCase {
    /**
     * Retrieves a list of continents.
     */
    suspend fun getContinents(): ResultState<List<Continent>>
}