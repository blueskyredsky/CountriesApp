package com.reza.feature.continents.presentation

/**
 * Sealed interface representing events related to details screen
 */
sealed interface ContinentsEvent {
    /**
     * Represents an event to fetch the list of continents.
     *
     * @property isRefreshing Indicates whether this request is part of a refresh operation.
     * Defaults to `false`.
     */
    data class GetContinents(val isRefreshing: Boolean = false) : ContinentsEvent

    /**
     * Represents an event to consume and clear any existing error messages.
     * This is typically used after an error has been handled or displayed.
     */
    data object ConsumeErrorMessage : ContinentsEvent
}