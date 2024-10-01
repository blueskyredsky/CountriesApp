package com.reza.feature.home.presentation

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reza.countriesapp.ui.theme.CountriesAppTheme
import com.reza.feature.home.R
import com.reza.feature.home.domain.model.Continent
import com.reza.systemdesign.ui.common.LoadingItem
import com.reza.systemdesign.ui.util.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ContinentsScreen(
    viewModel: HomeViewModel,
    onSelectContinent: (Continent) -> Unit
) {
    val state by viewModel.homeState.collectAsState()
    val screenState = rememberHomeScreenState()

    Scaffold(
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
                        screenState.showSnackBar(
                            message = targetState.errorMessage,
                            actionLabel = stringResource(R.string.retry),
                            resultCallback = { result ->
                                when (result) {
                                    SnackbarResult.ActionPerformed ->
                                        viewModel.onEvent(HomeEvent.GetContinents)

                                    SnackbarResult.Dismissed -> {
                                        viewModel.consumeErrorMessage()
                                    }
                                }
                            }
                        )
                    } else {
                        ContinentList(
                            isRefreshing = state.isLoading,
                            continents = state.continents,
                            onSelectContinent = onSelectContinent,
                            onRefresh = {
                                viewModel.onEvent(HomeEvent.GetContinents)
                            }
                        )
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
private fun ContinentList(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    continents: List<ContinentView>,
    onSelectContinent: (Continent) -> Unit,
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
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun ContinentItem(
    modifier: Modifier = Modifier,
    continentView: ContinentView,
    onSelectContinent: (Continent) -> Unit
) {
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable { onSelectContinent(continentView.continent) }
            .testTag(Constants.UiTags.ContinentItem.customName),
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