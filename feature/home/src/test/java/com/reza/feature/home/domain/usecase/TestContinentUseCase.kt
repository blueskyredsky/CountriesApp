package com.reza.feature.home.domain.usecase

import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent
import kotlinx.coroutines.delay

class TestContinentUseCase: ContinentsUseCase {

    override suspend fun getContinents(): ResultState<List<Continent>> {
        delay(1L)
        return ResultState.Success(Continent.LIST_OF_CONTINENTS)
    }
}