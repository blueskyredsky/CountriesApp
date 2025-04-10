package com.reza.feature.home.presentation

import androidx.annotation.DrawableRes
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.reza.feature.home.domain.model.Continent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable

sealed interface HomeUiState {

    /**
     * Represents the success state. This state is immutable, meaning its properties cannot be changed after it is created.
     */
    @Immutable
    data class Success(val continents: List<ContinentView>) : HomeUiState

    /**
     * Represents the error state.
     */
    data class Error(val errorMessage: String?) : HomeUiState

    /**
     * Represents the empty state.
     */
    data object Empty : HomeUiState

    /**
     * Represents the loading state.
     */
    data object Loading : HomeUiState

    /**
     * Represents the refreshing state.
     */
    data object Refreshing : HomeUiState
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
