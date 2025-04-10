package com.reza.details.presentation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
internal class DetailsStateHolder(
    val snackBarHostState: SnackbarHostState,
    private val scope: CoroutineScope,
    isSearchVisible: Boolean = false,
    searchQuery: String = ""
) {
    var isSearchVisible by mutableStateOf(isSearchVisible)
        private set

    var searchQuery by mutableStateOf(searchQuery)

    fun clearSearchQuery() {
        searchQuery = ""
    }

    fun toggleSearchVisibility() {
        isSearchVisible = !isSearchVisible
    }

    fun showSnackBar(
        message: String,
        actionLabel: String,
        resultCallback: (SnackbarResult) -> Unit
    ) {
        scope.launch {
            val result =
                snackBarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel,
                    duration = SnackbarDuration.Short
                )
            resultCallback(result)
        }
    }

    companion object {
        fun detailsStateSaver(
            snackBarHostState: SnackbarHostState,
            scope: CoroutineScope
        ) = Saver<DetailsStateHolder, Any>(
            save = { value ->
                listOf(value.isSearchVisible, value.searchQuery)
            },
            restore = { value ->
                val list = value as? List<*>
                if (list?.size == 2) {
                    val isSearchVisible = list[0] as? Boolean ?: false
                    val searchQuery = list[1] as? String ?: ""
                    DetailsStateHolder(
                        snackBarHostState = snackBarHostState,
                        scope = scope,
                        isSearchVisible = isSearchVisible,
                        searchQuery = searchQuery
                    )
                } else {
                    null // Return null if the saved data is invalid
                }
            }
        )
    }
}

@Composable
internal fun rememberDetailsScreenState(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope = rememberCoroutineScope(),
) = rememberSaveable(
    saver = DetailsStateHolder.detailsStateSaver(snackBarHostState, scope)
) {
    DetailsStateHolder(scope = scope, snackBarHostState = snackBarHostState)
}