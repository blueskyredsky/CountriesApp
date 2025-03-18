package com.reza.countriesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.reza.details.presentation.navigation.detailsScreen
import com.reza.details.presentation.navigation.navigateToDetails
import com.reza.feature.home.presentation.navigation.HomeRoute
import com.reza.feature.home.presentation.navigation.homeScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeRoute
    ) {
        homeScreen { continent ->
            navController.navigateToDetails(continent.code)
        }

        detailsScreen {
            navController.navigateUp()
        }
    }
}