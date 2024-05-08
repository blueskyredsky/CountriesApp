package com.reza.countriesapp.presentation.details

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.reza.countriesapp.domain.model.Country
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal data class DetailsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val countries: List<Country?>? = null
)

@Stable
internal class DetailsStateHolder(
    val snackBarHostState: SnackbarHostState,
    private val scope: CoroutineScope
) {
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
}

@Composable
internal fun rememberDetailsScreenState(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope = rememberCoroutineScope(),
) = remember {
    DetailsStateHolder(scope = scope, snackBarHostState = snackBarHostState)
}
