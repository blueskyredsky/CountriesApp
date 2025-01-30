package com.reza.countriesapp.presentation.details

internal sealed interface DetailsEvent {
    object GetCountries: DetailsEvent
}