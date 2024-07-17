package com.reza.feature.home.presentation

sealed interface HomeEvent {
    object GetContinents: HomeEvent
}