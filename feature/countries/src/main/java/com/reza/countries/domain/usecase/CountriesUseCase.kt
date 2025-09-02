package com.reza.countries.domain.usecase

import com.reza.common.domain.model.ResultState
import com.reza.countries.domain.model.Country

/**
 * A use case interface for retrieving country data.
 */
internal interface CountriesUseCase {
    /**
     * Retrieves a list of countries for a given continent code.
     */
    suspend fun getCountries(code: String): ResultState<List<Country>>
}