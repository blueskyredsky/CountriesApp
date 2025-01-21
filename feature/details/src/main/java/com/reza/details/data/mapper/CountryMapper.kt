package com.reza.details.data.mapper

import com.reza.ContinentQuery
import com.reza.details.domain.model.Country

fun ContinentQuery.Continent.toCountry(): List<Country> {
    return this.countries.map { countryQuery ->
        Country(
            name = countryQuery.name,
            emoji = countryQuery.emoji,
            currency = countryQuery.currency ?: "-",
            capital = countryQuery.capital ?: "-",
            phone = countryQuery.phone,
            states = countryQuery.states.map(ContinentQuery.State::name),
            languages = countryQuery.languages.map(ContinentQuery.Language::name)
        )
    }
}