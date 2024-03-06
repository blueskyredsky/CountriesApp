package com.reza.countriesapp.presentation.home

import com.reza.countriesapp.domain.model.Continent
import javax.annotation.concurrent.Immutable

@Immutable
data class HomeState(
    val continents: List<Continent> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
