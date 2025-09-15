package com.reza.feature.continents.presentation

import androidx.annotation.DrawableRes
import com.reza.feature.continents.domain.model.Continent
import javax.annotation.concurrent.Immutable

sealed interface ContinentsUiState {

    /**
     * Represents the success state. This state is immutable, meaning its properties cannot be changed after it is created.
     */
    @Immutable
    data class Success(val continents: List<ContinentView>) : ContinentsUiState

    /**
     * Represents the error state.
     */
    data class Error(val errorMessage: String?) : ContinentsUiState

    /**
     * Represents the empty state.
     */
    data object Empty : ContinentsUiState

    /**
     * Represents the loading state.
     */
    data object Loading : ContinentsUiState

    /**
     * Represents the refreshing state.
     */
    data object Refreshing : ContinentsUiState
}

/**
 * Represents a view of a continent.
 *
 * This data class holds information about a continent, including its name and an associated image resource.
 */
data class ContinentView(
    val continent: Continent,
    @DrawableRes val imageResource: Int
)
