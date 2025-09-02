package com.reza.feature.continents.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.reza.feature.continents.domain.model.Continent
import com.reza.feature.continents.presentation.ContinentsScreen
import com.reza.feature.continents.presentation.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute // route to home screen

fun NavGraphBuilder.homeScreen(onSelectContinent: (Continent) -> Unit) {
    composable<HomeRoute> { backStackEntry ->
        val viewModel = hiltViewModel<HomeViewModel>(backStackEntry)
        ContinentsScreen(
            viewModel = viewModel,
            onSelectContinent = onSelectContinent
        )
    }
}