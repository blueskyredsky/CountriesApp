package com.reza.countriesapp.presentation.details

import com.reza.countriesapp.presentation.home.HomeEvent

sealed interface DetailsEvent {
    object GetCountries: DetailsEvent
}