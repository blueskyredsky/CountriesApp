package com.reza.details.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.reza.systemdesign.ui.common.LoadingItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailsScreen(viewModel: DetailsViewModel, onBackClick: () -> Unit) {

    val detailsUiState by viewModel.detailsUiState.collectAsStateWithLifecycle()
    val screenState = rememberDetailsScreenState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = "search...",
                        onValueChange = {

                        })
                    //Text(stringResource(R.string.countries))
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "back"
                        )
                    }
                },
                actions = {
//                    IconButton(onClick = {
//
//                    }) {
//                        Icon(Icons.Default.Search, contentDescription = "search")
//                    }
                }
            )
        },
        content = { innerPaddingModifier ->
            Crossfade(
                modifier = Modifier.padding(innerPaddingModifier),
                targetState = detailsUiState,
                animationSpec = tween(500),
                label = "cross fade"
            ) { targetState ->
                when (targetState) {
                    DetailsUiState.Empty -> Unit

                    is DetailsUiState.Error -> {
                        targetState.errorMessage?.let { errorMessage ->
                            screenState.showSnackBar(
                                message = errorMessage,
                                actionLabel = stringResource(com.reza.common.R.string.retry),
                                resultCallback = { result ->
                                    when (result) {
                                        SnackbarResult.ActionPerformed ->
                                            viewModel.onEvent(DetailsEvent.GetCountries)

                                        SnackbarResult.Dismissed -> {
                                            viewModel.onEvent(DetailsEvent.ConsumeErrorMessage)
                                        }
                                    }
                                }
                            )
                        }
                    }

                    DetailsUiState.Loading -> LoadingItem()

                    is DetailsUiState.Success -> {

                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = screenState.snackBarHostState)
        }
    )
}

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
