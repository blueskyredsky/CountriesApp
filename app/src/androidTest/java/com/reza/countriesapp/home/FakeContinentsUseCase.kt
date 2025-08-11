package com.reza.countriesapp.home

import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent
import com.reza.feature.home.domain.usecase.ContinentsUseCase
import java.io.IOException
import javax.inject.Inject

class FakeContinentsUseCase @Inject constructor() : ContinentsUseCase {

    var shouldReturnError: Boolean = false
    var errorType: Throwable? = null
    var successData: List<Continent> = emptyList()

    init {
        successData = listOf(
            Continent(code = Continent.ASIA_CODE, name = Continent.ASIA),
            Continent(code = Continent.EUROPE_CODE, name = Continent.EUROPE),
            Continent(code = Continent.AFRICA_CODE, name = Continent.AFRICA)
        )
    }

    override suspend fun getContinents(): ResultState<List<Continent>> {
        return if (shouldReturnError) {
            ResultState.Failure(errorType?.message ?: "Simulated generic error for getContinents")
        } else {
            ResultState.Success(successData)
        }
    }


    fun setSuccess(
        data: List<Continent> = listOf(
            Continent(code = Continent.ASIA_CODE, name = Continent.ASIA),
            Continent(code = Continent.EUROPE_CODE, name = Continent.EUROPE)
        )
    ) {
        shouldReturnError = false
        successData = data
    }

    fun setError(error: Throwable = IOException("Simulated network error for continents")) {
        shouldReturnError = true
        errorType = error
    }

    fun setEmptyContinents() {
        shouldReturnError = false
        successData = emptyList()
    }

    fun reset() {
        shouldReturnError = false
        errorType = null
        successData = listOf(
            Continent(code = Continent.ASIA_CODE, name = Continent.ASIA),
            Continent(code = Continent.EUROPE_CODE, name = Continent.EUROPE),
            Continent(code = Continent.AFRICA_CODE, name = Continent.AFRICA)
        )
    }
}