package com.reza.feature.continents.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.reza.feature.continents.domain.model.Continent
import com.reza.feature.continents.presentation.ContinentsScreen
import com.reza.feature.continents.presentation.ContinentsViewModel
import kotlinx.serialization.Serializable

@Serializable
data object ContinentsRoute // route to continents screen

fun NavGraphBuilder.continentsScreen(onSelectContinent: (Continent) -> Unit) {
    composable<ContinentsRoute> { backStackEntry ->
        val viewModel = hiltViewModel<ContinentsViewModel>(backStackEntry)
        ContinentsScreen(
            viewModel = viewModel,
            onSelectContinent = onSelectContinent
        )
    }
}