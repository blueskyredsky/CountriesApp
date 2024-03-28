package com.reza.countriesapp.presentation.details

sealed interface DetailsEvent {
    object GetCountries: DetailsEvent
}