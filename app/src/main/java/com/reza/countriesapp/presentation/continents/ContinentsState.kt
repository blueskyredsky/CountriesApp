package com.reza.countriesapp.presentation.continents

import com.reza.countriesapp.domain.model.Continent
import javax.annotation.concurrent.Immutable

@Immutable
data class ContinentsState(
    val continents: List<Continent> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
