package com.reza.details.domain.repository

import com.reza.common.domain.model.ResultState
import com.reza.details.domain.model.Country

interface CountriesRepository {
    suspend fun getCountries(code: String): ResultState<List<Country>>
}