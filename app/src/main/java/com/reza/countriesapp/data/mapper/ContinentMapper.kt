package com.reza.countriesapp.data.mapper

import com.reza.ContinentQuery
import com.reza.countriesapp.domain.entity.ContinentEntity
import com.reza.countriesapp.domain.entity.CountryEntity

fun ContinentQuery.Continent.toContinent(): ContinentEntity {
    return ContinentEntity(
        name = name,
        code = code,
        countries = countries.map {
            CountryEntity(
                name = it.name,
                emoji = it.emoji,
                currency = it.currency,
                capital = it.capital,
                phone = it.phone,
                states = it.states.map { state -> state.name },
                languages = it.languages.map { language -> language.name }
            )
        }
    )
}