package com.reza.feature.home.presentation

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.reza.countriesapp.ui.theme.CountriesAppTheme
import com.reza.feature.home.R
import com.reza.feature.home.domain.model.Continent
import com.reza.systemdesign.ui.common.ShimmerLazyColumn
import com.reza.systemdesign.ui.util.UiTags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContinentsScreen(
    viewModel: HomeViewModel,
    onSelectContinent: (Continent) -> Unit
) {
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    val screenState = rememberHomeScreenState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeEvent.GetContinents())
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
                isRefreshing = homeUiState == HomeUiState.Refreshing,
                onRefresh = {
                    viewModel.onEvent(HomeEvent.GetContinents(true))
                },
                modifier = Modifier
                    .padding(innerPaddingModifier)
                    .fillMaxSize(),
                indicator = {
                    Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isRefreshing = homeUiState == HomeUiState.Refreshing,
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
                        HomeUiState.Empty -> Unit

                        is HomeUiState.Success -> {
                            ContinentList(
                                continents = targetState.continents,
                                onSelectContinent = onSelectContinent
                            )
                        }

                        is HomeUiState.Error -> {
                            targetState.errorMessage?.let { errorMessage ->
                                screenState.showSnackBar(
                                    message = errorMessage,
                                    actionLabel = stringResource(com.reza.common.R.string.retry),
                                    resultCallback = { result ->
                                        when (result) {
                                            SnackbarResult.ActionPerformed ->
                                                viewModel.onEvent(HomeEvent.GetContinents(true))

                                            SnackbarResult.Dismissed -> {
                                                viewModel.onEvent(HomeEvent.ConsumeErrorMessage)
                                            }
                                        }
                                    }
                                )
                            }
                        }

                        HomeUiState.Loading -> {
                            ShimmerLazyColumn()
                        }
                        HomeUiState.Refreshing -> Unit
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

@Composable
private fun ContinentList(
    continents: List<ContinentView>,
    modifier: Modifier = Modifier,
    onSelectContinent: (Continent) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(
            key = { item -> item.continent.code },
            items = continents
        ) { continentView ->
            ContinentItem(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                continentView = continentView,
                onSelectContinent = onSelectContinent
            )
        }
    }
}

@Composable
private fun ContinentItem(
    modifier: Modifier = Modifier,
    continentView: ContinentView,
    onSelectContinent: (Continent) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable { onSelectContinent(continentView.continent) }
            .testTag(UiTags.HomeScreen.CONTINENT_ITEM),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp), text = continentView.continent.name
            )
            Image(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(id = continentView.imageResource),
                contentDescription = null
            )
        }
    }
}

@Preview(name = "Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun ContinentItemPreview() {
    CountriesAppTheme {
        ContinentItem(modifier = Modifier.fillMaxWidth(),
            continentView = ContinentView(
                continent = Continent(
                    name = "name",
                    code = "code"
                ),
                imageResource = R.drawable.ic_africa
            ),
            onSelectContinent = {})
    }
}