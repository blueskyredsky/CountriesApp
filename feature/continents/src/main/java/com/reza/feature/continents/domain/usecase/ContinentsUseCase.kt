package com.reza.feature.continents.domain.usecase

import com.reza.common.domain.model.ResultState
import com.reza.feature.continents.presentation.ContinentView

/**
 * A use case interface for retrieving continent data.
 */
interface ContinentsUseCase {
    /**
     * Retrieves a list of continents.
     */
    suspend fun getContinents(): ResultState<List<ContinentView>>
}