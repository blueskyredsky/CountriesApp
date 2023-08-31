package com.reza.countriesapp.data.mapper

import com.reza.ContinentQuery
import com.reza.countriesapp.domain.mapper.DomainMapper
import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.model.Country
import javax.inject.Inject

class ContinentMapper @Inject constructor() : DomainMapper<ContinentQuery.Continent, Continent> {
    override fun mapToDomainModel(model: ContinentQuery.Continent): Continent {
        return Continent(
            name = model.name,
            code = model.code,
            countries = model.countries.map {
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
        )
    }

    override fun mapFromDomainModel(domainModel: Continent): ContinentQuery.Continent {
        return ContinentQuery.Continent(
            countries = domainModel.countries?.map {
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
            name = domainModel.name ?: "",
            code = domainModel.code ?: ""
        )
    }

}