package com.reza.countriesapp.presentation.continents

sealed interface ContinentsEvent {
    object RequestContinents: ContinentsEvent
}
