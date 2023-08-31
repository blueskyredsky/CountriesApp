package com.reza.countriesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.reza.countriesapp.presentation.details.DetailsScreen
import com.reza.countriesapp.presentation.home.HomeScreen

private const val HOME = "home"
private const val DETAIL = "detail"

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(HOME) {
            HomeScreen {
                navController.navigate(DETAIL)
            }
        }

        composable(DETAIL) { DetailsScreen() }
    }
}
