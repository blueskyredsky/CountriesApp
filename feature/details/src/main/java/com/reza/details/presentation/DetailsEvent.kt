package com.reza.details.presentation

internal sealed interface DetailsEvent {
    data object GetCountries: DetailsEvent
    data object ConsumeErrorMessage: DetailsEvent
}