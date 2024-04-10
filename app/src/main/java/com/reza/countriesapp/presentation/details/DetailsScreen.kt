package com.reza.countriesapp.presentation.details

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.EmojiSupportMatch
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reza.countriesapp.R
import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.model.Country
import com.reza.countriesapp.presentation.common.LoadingItem
import com.reza.countriesapp.presentation.home.ContinentItem
import com.reza.countriesapp.presentation.home.ContinentView
import com.reza.countriesapp.presentation.home.HomeEvent
import com.reza.countriesapp.ui.theme.CountriesAppTheme
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
                .padding(vertical = 8.dp)
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
                key = country.name ?: "",
                value = country.emoji ?: ""
            )
            ItemRow(
                key = stringResource(R.string.currency),
                value = country.currency ?: "-"
            )
            ItemRow(
                key = stringResource(R.string.capital),
                value = country.capital ?: "-"
            )
            ItemRow(
                key = stringResource(R.string.phone),
                value = country.phone ?: "-"
            )
        }
    }
}

@Composable
fun ItemRow(
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


@Preview(
    showBackground = true
)
@Composable
fun ItemRowPreview() {
    CountriesAppTheme {
        ItemRow(
            modifier = Modifier,
            key = "key",
            value = "value"
        )
    }
}

@Preview(name = "Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun CountryItemPreview() {
    CountriesAppTheme {
        CountryItem(
            modifier = Modifier.fillMaxWidth(),
            country = Country(
                name = "Iran",
                emoji = "\uD83C\uDDEE\uD83C\uDDF7",
                currency = "IRR",
                capital = "Tehran",
                phone = "+98",
                states = emptyList(),
                languages = listOf("Persian")
            )
        )
    }
}