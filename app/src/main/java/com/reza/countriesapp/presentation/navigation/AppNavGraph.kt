package com.reza.countriesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.reza.countriesapp.presentation.details.DetailsScreen
import com.reza.details.presentation.navigation.details
import com.reza.feature.home.presentation.navigation.home

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
        // todo think of using an inner navigation graph
        home { continent ->
            val route = "$DETAIL/${continent.code}"
            navController.navigate(route = route)
        }

        // todo working on connecting details
        //details()

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