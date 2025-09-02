package com.reza.countries.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.reza.countries.presentation.CountriesScreen
import com.reza.countries.presentation.DetailsViewModel
import kotlinx.serialization.Serializable

@Serializable
data class DetailsRoute(val continentCode: String, val continent: String) // route to details screen

fun NavGraphBuilder.detailsScreen(onBackClick: () -> Unit) {
    composable<DetailsRoute> { backStackEntry ->
        val detailsRoute: DetailsRoute = backStackEntry.toRoute()
        CountriesScreen(
            viewModel = hiltViewModel<DetailsViewModel>(backStackEntry),
            onBackClick = onBackClick,
            continentCode = detailsRoute.continentCode,
            continent = detailsRoute.continent
        )
    }
}

fun NavController.navigateToDetails(continentCode: String, continent: String) =
    navigate(route = DetailsRoute(continentCode = continentCode, continent = continent))