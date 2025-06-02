package com.reza.feature.home.data.datasource.remote

import com.apollographql.apollo.api.ApolloResponse
import com.reza.ContinentsQuery
import com.reza.feature.home.domain.model.Continent
import java.util.UUID

class FakeContinentRemoteDataSource: ContinentRemoteDataSource {

    override suspend fun getContinents(): ApolloResponse<ContinentsQuery.Data> {
        val continents = listOf(
            ContinentsQuery.Continent(
                code = Continent.AFRICA_CODE,
                name = Continent.AFRICA
            ),
            ContinentsQuery.Continent(
                code = Continent.ASIA_CODE,
                name = Continent.ASIA
            ),
            ContinentsQuery.Continent(
                code = Continent.EUROPE_CODE,
                name = Continent.EUROPE
            )
        )
        val data = ContinentsQuery.Data(continents)
        return ApolloResponse.Builder(
            operation = ContinentsQuery(),
            requestUuid = UUID.randomUUID()
        ).data(data)
            .build()
    }
}