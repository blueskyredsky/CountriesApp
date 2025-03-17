package com.reza.feature.home.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.reza.feature.home.domain.model.Continent
import com.reza.feature.home.presentation.ContinentsScreen
import com.reza.feature.home.presentation.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute // route to home screen

@Serializable
data object HomeBaseRoute // route to home base navigation graph

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

fun NavController.navigateToDetails() {

}