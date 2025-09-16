package com.reza.countries.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.reza.countries.presentation.CountriesScreen
import com.reza.countries.presentation.CountriesViewModel
import kotlinx.serialization.Serializable

@Serializable
data class CountriesRoute(val continentCode: String, val continent: String) // route to countries screen

fun NavGraphBuilder.countriesScreen(onBackClick: () -> Unit) {
    composable<CountriesRoute> { backStackEntry ->
        val countriesRoute: CountriesRoute = backStackEntry.toRoute()
        CountriesScreen(
            viewModel = hiltViewModel<CountriesViewModel>(backStackEntry),
            onBackClick = onBackClick,
            continentCode = countriesRoute.continentCode,
            continent = countriesRoute.continent
        )
    }
}

fun NavController.navigateToCountries(continentCode: String, continent: String) =
    navigate(route = CountriesRoute(continentCode = continentCode, continent = continent))