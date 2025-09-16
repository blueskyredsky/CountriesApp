package com.reza.countries.presentation

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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.reza.countries.domain.model.Country
import com.reza.feature.countries.R
import com.reza.systemdesign.ui.common.LoadingItem
import com.reza.systemdesign.ui.util.UiTags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CountriesScreen(
    viewModel: CountriesViewModel,
    onBackClick: () -> Unit,
    continentCode: String,
    continent: String
) {
    LaunchedEffect(key1 = continentCode) {
        viewModel.onEvent(CountriesEvent.GetCountries(continentCode))
    }

    val countriesUiState by viewModel.countriesUiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val filteredCountries by viewModel.filteredCountries.collectAsStateWithLifecycle()
    val screenState = rememberCountriesScreenState()

    BackHandler(enabled = screenState.isSearchVisible) {
        screenState.clearSearchQuery()
        viewModel.onEvent(CountriesEvent.Search(""))
        screenState.toggleSearchVisibility()
    }

    Scaffold(
        modifier = Modifier.testTag(UiTags.CountriesScreen.ROOT),
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
                                    viewModel.onEvent(CountriesEvent.Search(newQuery))
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
                            viewModel.onEvent(CountriesEvent.Search(""))
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
                targetState = countriesUiState,
                animationSpec = tween(500),
                label = "cross fade"
            ) { targetState ->
                when (targetState) {
                    CountriesUiState.Empty -> Unit

                    is CountriesUiState.Error -> {
                        targetState.errorMessage?.let { errorMessage ->
                            screenState.showSnackBar(
                                message = errorMessage,
                                actionLabel = stringResource(com.reza.common.R.string.retry),
                                resultCallback = { result ->
                                    when (result) {
                                        SnackbarResult.ActionPerformed ->
                                            viewModel.onEvent(
                                                CountriesEvent.GetCountries(
                                                    continentCode = continentCode,
                                                    isRefreshing = true
                                                )
                                            )

                                        SnackbarResult.Dismissed -> {
                                            viewModel.onEvent(CountriesEvent.ConsumeErrorMessage)
                                        }
                                    }
                                }
                            )
                        }
                    }

                    CountriesUiState.Loading -> LoadingItem()

                    is CountriesUiState.Success -> {
                        CountriesList(
                            isRefreshing = false,
                            countries = filteredCountries,
                            onRefresh = {
                                viewModel.onEvent(
                                    CountriesEvent.GetCountries(
                                        continentCode = continentCode,
                                        isRefreshing = true
                                    )
                                )
                            }
                        )
                    }

                    CountriesUiState.Refreshing -> {
                        // todo
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.testTag(UiTags.Common.SNACK_BAR),
                hostState = screenState.snackBarHostState)
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
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .testTag(UiTags.CountriesScreen.COUNTRY_ITEM),
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