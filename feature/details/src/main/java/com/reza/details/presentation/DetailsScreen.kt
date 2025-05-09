package com.reza.details.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.reza.details.R
import com.reza.details.domain.model.Country
import com.reza.systemdesign.ui.common.LoadingItem
import com.reza.systemdesign.ui.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailsScreen(
    viewModel: DetailsViewModel,
    onBackClick: () -> Unit,
    continentCode: String,
    continent: String
) {
    LaunchedEffect(key1 = continentCode) {
        viewModel.onEvent(DetailsEvent.GetCountries(continentCode))
    }

    val detailsUiState by viewModel.detailsUiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val filteredCountries by viewModel.filteredCountries.collectAsStateWithLifecycle()
    val screenState = rememberDetailsScreenState()

    BackHandler(enabled = screenState.isSearchVisible) {
        screenState.clearSearchQuery()
        viewModel.onEvent(DetailsEvent.Search(""))
        screenState.toggleSearchVisibility()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    AnimatedContent(screenState.isSearchVisible) { isSearchIconVisible ->
                        if (isSearchIconVisible) {
                            TextField(
                                value = searchQuery,
                                onValueChange = { newQuery ->
                                    viewModel.onEvent(DetailsEvent.Search(newQuery))
                                    screenState.searchQuery = newQuery
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White.copy(alpha = 0.5f),
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.5f),
                                    disabledContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                ),
                                placeholder = {
                                    Text(
                                        text = stringResource(R.string.search),
                                        color = Color.Black.copy(alpha = 0.5f)
                                    )
                                }
                            )
                        } else {
                            Text(continent)
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (screenState.isSearchVisible) {
                                screenState.clearSearchQuery()
                                screenState.toggleSearchVisibility()
                            } else {
                                onBackClick()
                            }
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (screenState.isSearchVisible) {
                            screenState.clearSearchQuery()
                            viewModel.onEvent(DetailsEvent.Search(""))
                        } else {
                            screenState.toggleSearchVisibility()
                        }
                    }) {
                        AnimatedContent(screenState.isSearchVisible) { isSearchIconVisible ->
                            if (isSearchIconVisible) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = stringResource(R.string.close)
                                )
                            } else {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = stringResource(R.string.search)
                                )
                            }
                        }
                    }
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
                                            viewModel.onEvent(
                                                DetailsEvent.GetCountries(
                                                    continentCode = continentCode,
                                                    isRefreshing = true
                                                )
                                            )

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
                        CountriesList(
                            isRefreshing = false,
                            countries = filteredCountries,
                            onRefresh = {
                                viewModel.onEvent(
                                    DetailsEvent.GetCountries(
                                        continentCode = continentCode,
                                        isRefreshing = true
                                    )
                                )
                            }
                        )
                    }

                    DetailsUiState.Refreshing -> {
                        // todo
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = screenState.snackBarHostState)
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CountriesList(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    countries: List<Country>,
    onRefresh: () -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { onRefresh() })

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            items(
                key = { item -> item.name },
                items = countries
            ) { country ->
                CountryItem(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    country = country
                )
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
private fun CountryItem(
    modifier: Modifier = Modifier,
    country: Country
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .testTag(Constants.UiTags.CountryItem.customName),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column {
            ItemRow(
                key = country.name,
                value = country.emoji
            )
            ItemRow(
                key = stringResource(R.string.currency),
                value = country.currency
            )
            ItemRow(
                key = stringResource(R.string.capital),
                value = country.capital
            )
            ItemRow(
                key = stringResource(R.string.phone),
                value = country.phone
            )
        }
    }
}

@Composable
private fun ItemRow(
    modifier: Modifier = Modifier,
    key: String,
    value: String
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = key
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = value,
        )
    }
}