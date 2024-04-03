package com.reza.countriesapp.presentation.home

import androidx.annotation.DrawableRes
import com.reza.countriesapp.domain.model.Continent
import javax.annotation.concurrent.Immutable

@Immutable
data class HomeState(
    val continents: List<ContinentView> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class ContinentView(
    val continent: Continent,
    @DrawableRes val imageResource: Int
)
