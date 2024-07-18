package com.reza.feature.home.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.reza.feature.home.domain.model.Continent
import com.reza.feature.home.presentation.ContinentsScreen
import com.reza.feature.home.presentation.HomeViewModel

private const val HOME = "home"

fun NavGraphBuilder.home(onSelectContinent: (Continent) -> Unit) {
    composable(HOME) { backStackEntry ->
        val viewModel = hiltViewModel<HomeViewModel>(backStackEntry)
        ContinentsScreen(
            viewModel = viewModel,
            onSelectContinent = onSelectContinent
        )
    }
}