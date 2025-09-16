package com.reza.countriesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.reza.countries.presentation.navigation.countriesScreen
import com.reza.countries.presentation.navigation.navigateToCountries
import com.reza.feature.continents.presentation.navigation.ContinentsRoute
import com.reza.feature.continents.presentation.navigation.continentsScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ContinentsRoute
    ) {
        continentsScreen { continent ->
            navController.navigateToCountries(continent = continent.name, continentCode = continent.code)
        }

        countriesScreen {
            navController.navigateUp()
        }
    }
}