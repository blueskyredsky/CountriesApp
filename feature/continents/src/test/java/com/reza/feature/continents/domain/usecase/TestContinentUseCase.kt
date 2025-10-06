package com.reza.feature.continents.domain.usecase

import com.reza.common.domain.model.ResultState
import com.reza.feature.continents.presentation.ContinentView
import kotlinx.coroutines.delay

class TestContinentUseCase: ContinentsUseCase {

    override suspend fun getContinents(): ResultState<List<ContinentView>> {
        delay(1L)
        return ResultState.Success(emptyList())
    }
}