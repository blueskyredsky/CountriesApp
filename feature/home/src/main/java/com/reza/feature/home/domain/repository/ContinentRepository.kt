package com.reza.feature.home.domain.repository

import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent

internal interface ContinentRepository {
    suspend fun getContinents(): ResultState<List<Continent>>
}