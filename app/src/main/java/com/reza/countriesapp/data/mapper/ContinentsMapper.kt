package com.reza.countriesapp.data.mapper

import com.reza.ContinentsQuery
import com.reza.countriesapp.domain.entity.ContinentEntity

fun ContinentsQuery.Continent.toContinent(): ContinentEntity {
    return ContinentEntity(
        name = name,
        code = code,
        countries = null
    )
}