package com.reza.feature.continents.data.mapper

import com.reza.ContinentsQuery
import com.reza.feature.continents.domain.model.Continent

fun ContinentsQuery.Continent.toContinent(): Continent {
    return Continent(
        name = this.name,
        code = this.code
    )
}