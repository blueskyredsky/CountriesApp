package com.reza.countriesapp.presentation.details

import com.reza.countriesapp.domain.model.Country

data class DetailsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val countries: List<Country?>? = null
)
