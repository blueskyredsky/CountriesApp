package com.reza.countriesapp.domain.usecase

import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.model.ResultState
import com.reza.countriesapp.util.Util
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
                ResultState.Success(Util.listOfContinents)
            } else {
                ResultState.Failure(error = "")
            }
        }
}