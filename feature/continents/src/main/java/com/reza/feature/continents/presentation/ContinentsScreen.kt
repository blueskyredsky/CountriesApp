package com.reza.feature.continents.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.reza.feature.continents.R
import com.reza.feature.continents.domain.model.Continent
import com.reza.feature.continents.presentation.components.ContinentList
import com.reza.systemdesign.ui.common.ShimmerLazyColumn
import com.reza.systemdesign.ui.util.UiTags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContinentsScreen(
    viewModel: ContinentsViewModel,
    onSelectContinent: (Continent) -> Unit
) {
    val homeUiState by viewModel.continentsUiState.collectAsStateWithLifecycle()
    val screenState = rememberContinentsScreenState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(ContinentsEvent.GetContinents())
    }

    Scaffold(
        modifier = Modifier.testTag(UiTags.HomeScreen.ROOT),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.continents))
                }
            )
        },
        content = { innerPaddingModifier ->
            val state = rememberPullToRefreshState()
            PullToRefreshBox(
                state = state,
                isRefreshing = homeUiState == ContinentsUiState.Refreshing,
                onRefresh = {
                    viewModel.onEvent(ContinentsEvent.GetContinents(true))
                },
                modifier = Modifier
                    .padding(innerPaddingModifier)
                    .fillMaxSize(),
                indicator = {
                    Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isRefreshing = homeUiState == ContinentsUiState.Refreshing,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        state = state
                    )
                }
            ) {
                Crossfade(
                    targetState = homeUiState,
                    animationSpec = tween(500),
                    label = "cross fade"
                ) { targetState ->
                    when (targetState) {
                        ContinentsUiState.Empty -> Unit

                        is ContinentsUiState.Success -> {
                            ContinentList(
                                continents = targetState.continents,
                                onSelectContinent = onSelectContinent
                            )
                        }

                        is ContinentsUiState.Error -> {
                            targetState.errorMessage?.let { errorMessage ->
                                screenState.showSnackBar(
                                    message = errorMessage,
                                    actionLabel = stringResource(com.reza.common.R.string.retry),
                                    resultCallback = { result ->
                                        when (result) {
                                            SnackbarResult.ActionPerformed ->
                                                viewModel.onEvent(ContinentsEvent.GetContinents(true))

                                            SnackbarResult.Dismissed -> {
                                                viewModel.onEvent(ContinentsEvent.ConsumeErrorMessage)
                                            }
                                        }
                                    }
                                )
                            }
                        }

                        ContinentsUiState.Loading -> {
                            ShimmerLazyColumn()
                        }
                        ContinentsUiState.Refreshing -> Unit
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.testTag(UiTags.Common.SNACK_BAR),
                hostState = screenState.snackBarHostState
            )
        }
    )
}