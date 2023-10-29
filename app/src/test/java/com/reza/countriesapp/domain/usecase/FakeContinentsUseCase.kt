package com.reza.countriesapp.domain.usecase

import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.model.ResultState
import com.reza.countriesapp.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FakeContinentsUseCase(private val isSuccessful: Boolean = true) : ContinentsUseCase {
    override suspend fun getContinents(): ResultState<List<Continent>> =
        withContext(Dispatchers.Default) {
            delay(1000L)
            if (isSuccessful) {
                ResultState.Success(Util.listOfContinents)
            } else {
                ResultState.Failure(error = "")
            }
        }
}