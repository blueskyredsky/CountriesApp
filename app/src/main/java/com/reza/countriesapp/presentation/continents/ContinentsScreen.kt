package com.reza.countriesapp.presentation.continents

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reza.countriesapp.R
import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.presentation.common.LoadingItem
import com.reza.countriesapp.ui.theme.CountriesAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContinentsScreen(
    modifier: Modifier = Modifier,
    onSelectContinent: (Continent) -> Unit
) {

    val viewModel = hiltViewModel<ContinentsViewModel>()
    val state by viewModel.continentsState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

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
                modifier = modifier.padding(innerPaddingModifier),
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
                                    viewModel.onEvent(ContinentsEvent.RequestContinents)

                                SnackbarResult.Dismissed -> Unit
                            }
                        }
                    } else {
                        ContinentList(
                            isRefreshing = state.isLoading,
                            continents = state.continents,
                            onSelectContinent = onSelectContinent,
                            onRefresh = {
                                viewModel.onEvent(ContinentsEvent.RequestContinents)
                            }
                        )
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ContinentList(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    continents: List<Continent>,
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
                key = { item -> item.code ?: "" },
                items = continents
            ) { continent ->
                ContinentItem(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    item = continent,
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
    item: Continent,
    onSelectContinent: (Continent) -> Unit
) {
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable { onSelectContinent(item) },
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), text = item.name ?: ""
        )
    }
}

@Preview(name = "Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun ContinentItemPreview() {
    CountriesAppTheme {
        ContinentItem(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
            item = Continent(name = "name", code = "code"),
            onSelectContinent = {})
    }
}