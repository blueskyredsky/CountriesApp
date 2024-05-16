package com.reza.countriesapp.presentation.home

import androidx.annotation.DrawableRes
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.reza.countriesapp.domain.model.Continent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable

@Immutable
internal data class HomeState(
    val continents: List<ContinentView> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

internal data class ContinentView(
    val continent: Continent,
    @DrawableRes val imageResource: Int
)

@Stable
internal class HomeStateHolder(
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
internal fun rememberHomeScreenState(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope = rememberCoroutineScope(),
) = remember {
    HomeStateHolder(scope = scope, snackBarHostState = snackBarHostState)
}