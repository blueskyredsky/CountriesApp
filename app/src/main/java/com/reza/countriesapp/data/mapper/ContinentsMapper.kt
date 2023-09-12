package com.reza.countriesapp.data.mapper

import com.reza.ContinentsQuery
import com.reza.countriesapp.domain.model.Continent
import javax.inject.Inject

class ContinentsMapper @Inject constructor() : DomainMapper<ContinentsQuery.Continent, Continent> {
    override fun mapToDomainModel(model: ContinentsQuery.Continent): Continent {
        return Continent(
            name = model.name,
            code = model.code
        )
    }
}