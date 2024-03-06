package com.reza.countriesapp.presentation.home

sealed interface HomeEvent {
    object RequestHome: HomeEvent
}
