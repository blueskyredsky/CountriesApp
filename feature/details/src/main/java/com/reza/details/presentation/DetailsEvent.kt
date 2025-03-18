package com.reza.details.presentation

internal sealed interface DetailsEvent {
    data class GetCountries(val continentCode: String) : DetailsEvent
    data object ConsumeErrorMessage : DetailsEvent
    data object Refresh : DetailsEvent
}