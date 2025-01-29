package com.reza.details.presentation

import androidx.compose.runtime.Immutable
import com.reza.details.domain.model.Country

/**
 * Represents the UI state for the details screen.
 */
sealed interface DetailsUiState {

    /**
     * Represents the success state, where the list of countries is available.
     */
    @Immutable
    data class Success(val continents: List<Country>) : DetailsUiState

    /**
     * Represents the error state, where an error occurred while fetching the data.
     */
    data class Error(val errorMessage: String?) : DetailsUiState

    /**
     * Represents the empty state, where there is no data to display.
     */
    data object Empty : DetailsUiState

    /**
     * Represents the loading state, where the data is being fetched.
     */
    data object Loading : DetailsUiState
}