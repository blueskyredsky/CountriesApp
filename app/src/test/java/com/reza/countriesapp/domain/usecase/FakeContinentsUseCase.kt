package com.reza.countriesapp.domain.usecase

import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.model.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FakeContinentsUseCase(private val dispatcher: CoroutineDispatcher) : ContinentsUseCase {

    private var isSuccessful: Boolean = true

    fun setSuccessful(isSuccessful: Boolean) {
        this.isSuccessful = isSuccessful
    }

    override suspend fun getContinents(): ResultState<List<Continent>> =
        withContext(dispatcher) {
            delay(100L)
            if (isSuccessful) {
                ResultState.Success(Continent.LIST_OF_CONTINENTS)
            } else {
                ResultState.Failure(error = "")
            }
        }
}