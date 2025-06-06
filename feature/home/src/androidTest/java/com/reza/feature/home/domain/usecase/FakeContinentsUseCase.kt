package com.reza.feature.home.domain.usecase

import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent

class FakeContinentsUseCase: ContinentsUseCase {

    override suspend fun getContinents(): ResultState<List<Continent>> {
        return ResultState.Success(
            listOf(
                Continent(code = Continent.ASIA_CODE, name = Continent.ASIA),
                Continent(code = Continent.EUROPE_CODE, name = Continent.EUROPE)
            )
        )
    }
}