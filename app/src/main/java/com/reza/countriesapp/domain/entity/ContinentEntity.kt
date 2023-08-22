package com.reza.countriesapp.domain.entity

data class ContinentEntity(
    val name: String?,
    val code: String?,
    val countries: List<CountryEntity?>?
)
