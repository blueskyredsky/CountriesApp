package com.reza.countriesapp.domain.usecase.countries

import com.reza.countriesapp.domain.model.Country
import com.reza.countriesapp.domain.model.ResultState
import com.reza.countriesapp.domain.usecase.countries.CountriesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FakeCountriesUseCase(private val dispatcher: CoroutineDispatcher) : CountriesUseCase {

    private var isSuccessful: Boolean = true

    fun setSuccessful(isSuccessful: Boolean) {
        this.isSuccessful = isSuccessful
    }

    override suspend fun getCountries(code: String): ResultState<List<Country?>?> =
        withContext(dispatcher) {
            delay(100L)
            if (isSuccessful) {
                ResultState.Success(Country.LIST_OF_COUNTRIES)
            } else {
                ResultState.Failure(error = "")
            }
        }
}