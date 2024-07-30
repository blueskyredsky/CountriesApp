package com.reza.countriesapp.data.model

import com.reza.ContinentQuery
import com.reza.ContinentsQuery
import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.model.Country

fun ContinentQuery.Continent.toCountry(): List<Country?> {
    return this.countries.map {
        Country(
            name = it.name,
            emoji = it.emoji,
            currency = it.currency,
            capital = it.capital,
            phone = it.phone,
            states = it.states.map { state -> state.name },
            languages = it.languages.map { language -> language.name }
        )
    }
}