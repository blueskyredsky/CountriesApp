package com.reza.countriesapp.data.datasourse.remote.continent

import com.apollographql.apollo3.api.ApolloResponse
import com.reza.ContinentQuery
import com.reza.ContinentsQuery

interface ContinentDataSource {
    suspend fun getContinents(): ApolloResponse<ContinentsQuery.Data>
}