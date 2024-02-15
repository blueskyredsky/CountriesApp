package com.reza.countriesapp.presentation.details

import com.reza.countriesapp.domain.model.Country

data class CountriesState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val countries: List<Country> = emptyList()
)
