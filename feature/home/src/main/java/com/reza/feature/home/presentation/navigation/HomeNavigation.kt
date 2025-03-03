package com.reza.feature.home.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.reza.feature.home.domain.model.Continent
import com.reza.feature.home.presentation.ContinentsScreen
import com.reza.feature.home.presentation.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data object HomeBaseRoute

fun NavGraphBuilder.homeSection(onSelectContinent: (Continent) -> Unit) {
    navigation<HomeBaseRoute>(startDestination = HomeRoute) {
        composable<HomeRoute> { backStackEntry ->
            val viewModel = hiltViewModel<HomeViewModel>(backStackEntry)
            ContinentsScreen(
                viewModel = viewModel,
                onSelectContinent = onSelectContinent
            )
        }
    }
}