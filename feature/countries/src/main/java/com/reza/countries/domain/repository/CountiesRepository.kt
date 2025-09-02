package com.reza.countries.domain.repository

import com.reza.common.domain.model.ResultState
import com.reza.countries.domain.model.Country

/**
 * A repository interface for retrieving country data.
 */
internal interface CountriesRepository {
    /**
     * Retrieves a list of countries for a given continent code.
     */
    suspend fun getCountries(code: String): ResultState<List<Country>>
}