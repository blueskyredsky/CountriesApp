package com.reza.countriesapp.domain.usecase

import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FakeContinentsUseCase : ContinentsUseCase {
    override suspend fun getContinents(): List<Continent> = withContext(Dispatchers.Default) {
        delay(1000L)
        Util.listOfContinents
    }
}