package com.reza.countriesapp.presentation.details

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reza.countriesapp.R
import com.reza.countriesapp.domain.model.Country
import com.reza.countriesapp.presentation.common.LoadingItem
import com.reza.countriesapp.presentation.home.HomeEvent
import com.reza.countriesapp.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    continentCode: String?,
    viewModel: DetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.countriesState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.countries))
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "back")
                    }
                }
            )
        },
        content = { innerPaddingModifier ->
            Crossfade(
                modifier = Modifier.padding(innerPaddingModifier),
                targetState = state,
                animationSpec = tween(500),
                label = "cross fade"
            ) { targetState ->
                if (targetState.isLoading) {
                    LoadingItem()
                } else {
                    if (targetState.errorMessage != null) {
                        val actionLabel = stringResource(id = R.string.retry)
                        LaunchedEffect(key1 = Unit) {
                            val result = snackbarHostState.showSnackbar(
                                message = targetState.errorMessage,
                                actionLabel = actionLabel
                            )
                            when (result) {
                                SnackbarResult.ActionPerformed ->
                                    viewModel.onEvent(HomeEvent.GetContinents)

                                SnackbarResult.Dismissed -> {
                                    viewModel.consumeErrorMessage()
                                }
                            }
                        }
                    } else {
                        state.countries?.let {
                            CountriesList(
                                isRefreshing = state.isLoading,
                                countries = it,
                                onRefresh = {
                                    viewModel.onEvent(HomeEvent.GetContinents)
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountriesList(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    countries: List<Country?>,
    onRefresh: () -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { onRefresh() })

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            items(
                key = { item -> item?.name ?: "" },
                items = countries
            ) { country ->
                country?.let {
                    CountryItem(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        country = it
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun CountryItem(
    modifier: Modifier = Modifier,
    country: Country
) {
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .testTag(Constants.UiTags.ContinentItem.customName),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), text = country.name ?: ""
        )
    }
}