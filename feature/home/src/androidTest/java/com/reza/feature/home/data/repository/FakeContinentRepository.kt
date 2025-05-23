package com.reza.feature.home.data.repository

import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent
import com.reza.feature.home.domain.repository.ContinentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeContinentRepository : ContinentRepository {

    private var shouldSimulateError: Boolean = false

    private val _continents = MutableStateFlow<List<Continent>>(emptyList())
    val continents: StateFlow<List<Continent>> = _continents

    fun setContinents(continents: List<Continent>) {
        _continents.value = continents
    }

    override suspend fun getContinents(): ResultState<List<Continent>> {
        return if (shouldSimulateError) {
            ResultState.Failure("Simulated network error!")
        } else {
            ResultState.Success(_continents.value)
        }
    }

    fun simulateError() {
        shouldSimulateError = true
    }

    fun clearSimulatedError() {
        shouldSimulateError = false
    }
}