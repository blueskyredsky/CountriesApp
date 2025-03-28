package com.reza.feature.home.data.mapper

import com.reza.ContinentsQuery
import com.reza.feature.home.domain.model.Continent

fun ContinentsQuery.Continent.toContinent(): Continent {
    return Continent(
        name = this.name,
        code = this.code
    )
}