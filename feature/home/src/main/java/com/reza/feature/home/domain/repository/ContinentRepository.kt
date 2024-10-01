package com.reza.feature.home.domain.repository

import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent

/**
 * A repository interface for accessing continent data.
 */
internal interface ContinentRepository {

    /**
     * Retrieves a list of continents.
     */
    suspend fun getContinents(): ResultState<List<Continent>>
}