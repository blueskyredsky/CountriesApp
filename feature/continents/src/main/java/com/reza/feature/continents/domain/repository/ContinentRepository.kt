package com.reza.feature.continents.domain.repository

import com.reza.common.domain.model.ResultState
import com.reza.feature.continents.domain.model.Continent

/**
 * A repository interface for accessing continent data.
 */
internal interface ContinentRepository {

    /**
     * Retrieves a list of continents.
     */
    suspend fun getContinents(): ResultState<List<Continent>>
}