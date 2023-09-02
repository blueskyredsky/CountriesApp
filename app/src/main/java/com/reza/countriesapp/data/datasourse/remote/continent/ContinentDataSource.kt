package com.reza.countriesapp.data.datasourse.remote.continent

import com.reza.ContinentQuery
import com.reza.ContinentsQuery

interface ContinentDataSource {
    suspend fun getContinents(): List<ContinentsQuery.Continent>?
    suspend fun getContinent(code: String): ContinentQuery.Continent?
}