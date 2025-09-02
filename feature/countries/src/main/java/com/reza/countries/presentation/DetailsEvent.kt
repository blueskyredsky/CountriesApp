package com.reza.countries.presentation

/**
 * Sealed interface representing events related to home screen
 */
internal sealed interface DetailsEvent {
    /**
     * Represents an event to fetch the list of countries for a given continent.
     *
     * @property continentCode The code identifying the continent for which to fetch countries.
     * @property isRefreshing Indicates whether this request is part of a refresh operation.
     * Defaults to `false`.
     */
    data class GetCountries(val continentCode: String, val isRefreshing: Boolean = false) : DetailsEvent

    /**
     * Represents an event to consume and clear any existing error messages.
     * This is typically used after an error has been handled or displayed.
     */
    data object ConsumeErrorMessage : DetailsEvent

    /**
     * Represents an event to search for countries based on a given query
     */
    data class Search(val query: String) : DetailsEvent
}