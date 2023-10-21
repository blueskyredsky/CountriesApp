package com.reza.countriesapp.presentation.continents

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.presentation.common.LoadingItem
import com.reza.countriesapp.ui.theme.CountriesAppTheme

@Composable
fun ContinentsScreen(
    modifier: Modifier = Modifier, onSelectContinent: (Continent) -> Unit
) {
    val viewModel = hiltViewModel<ContinentsViewModel>()
    val state by viewModel.continentsState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(ContinentsEvent.RequestContinents)
    }

    Scaffold(modifier = modifier) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = state,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "Animated Content"
            ) { targetState ->
                if (targetState.isLoading) {
                    LoadingItem()
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            key = { item -> item.code ?: "" }, items = state.continents
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
                }
            }
        }
    }
}

@Composable
private fun ContinentItem(
    modifier: Modifier = Modifier, item: Continent, onSelectContinent: (Continent) -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onSelectContinent(item) },
        shape = RoundedCornerShape(8.dp),
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