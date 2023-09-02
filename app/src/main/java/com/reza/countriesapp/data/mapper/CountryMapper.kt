package com.reza.countriesapp.data.mapper

import com.reza.ContinentQuery
import com.reza.countriesapp.domain.common.DomainMapper
import com.reza.countriesapp.domain.model.Country
import javax.inject.Inject

class CountryMapper @Inject constructor() :
    DomainMapper<ContinentQuery.Continent, List<Country?>?> {
    override fun mapToDomainModel(model: ContinentQuery.Continent): List<Country?>? {
        return model.countries.map {
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

    override fun mapFromDomainModel(domainModel: List<Country?>?): ContinentQuery.Continent {
        return ContinentQuery.Continent(
            countries = domainModel?.map {
                ContinentQuery.Country(
                    name = it?.name ?: "",
                    emoji = it?.emoji ?: "",
                    currency = it?.currency ?: "",
                    capital = it?.capital,
                    phone = it?.phone ?: "",
                    states = it?.states?.map { state ->
                        ContinentQuery.State(name = state ?: "")
                    } ?: emptyList(),
                    languages = it?.languages?.map { language ->
                        ContinentQuery.Language(
                            name = language ?: ""
                        )
                    } ?: emptyList()
                )
            } ?: emptyList(),
        )
    }
}