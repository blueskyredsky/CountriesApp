package com.reza.countriesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.reza.countriesapp.presentation.details.DetailsScreen
import com.reza.countriesapp.presentation.home.ContinentsScreen

private const val HOME = "home"
const val CONTINENT_CODE = "continentCode"
private const val DETAIL = "detail"

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HOME
    ) {
        composable(HOME) {
            ContinentsScreen(
                onSelectContinent = { continent ->
                    val route = "$DETAIL/${continent.code}"
                    navController.navigate(route = route)
                }
            )
        }

        composable("$DETAIL/{$CONTINENT_CODE}") { backStackEntry ->
            DetailsScreen(
                continentCode = backStackEntry.arguments?.getString(CONTINENT_CODE),
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
}